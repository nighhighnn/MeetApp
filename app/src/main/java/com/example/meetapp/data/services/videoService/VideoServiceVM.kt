package com.example.meetapp.data.services.videoService

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ViewMode {
    GRID,
    SPOTLIGHT
}

@HiltViewModel
class VideoServiceVM @Inject constructor() : ViewModel() {

    private var rtcEngine: RtcEngine? = null
    private lateinit var videoConfig: VideoConfig
    private val activeVideoViews = mutableStateListOf<VideoView>()
    private val isVideoOn = mutableStateOf(true)
    private val isMicOn = mutableStateOf(true)
    private val viewMode = mutableStateOf(ViewMode.GRID)
    private val activeSpeakerUid = mutableStateOf("")
    private val showSpotlight = mutableStateOf(true)
    private val micToggleStates = mutableStateListOf<Boolean>()
    private lateinit var context: Context

    val activeVideos get() = activeVideoViews
    val currentViewMode get() = viewMode.value
    val isVideoEnabled get() = isVideoOn.value
    val isMicrophoneEnabled get() = isMicOn.value
    val spotlightVisible get() = showSpotlight.value
    val activeSpeaker get() = activeSpeakerUid.value
    val microphoneToggleStates get() = micToggleStates
    val currVideoConfig: VideoConfig get() = videoConfig
    val videoRtcEngine: RtcEngine? get() = rtcEngine

    fun initializeEngine(context: Context, config: VideoConfig) {

        if (rtcEngine != null)
            return

        this.videoConfig = config
        this.context = context
        val rtcConfig = RtcEngineConfig().apply {
            mContext = context
            mAppId = config.apiId
            mEventHandler = sessionEventHandler
        }

        try {
            rtcEngine = RtcEngine.create(rtcConfig).apply {
                enableVideo()
                startPreview()
                enableAudioVolumeIndication(700, 3, true)
            }
            addVideoView(config.userId?.toInt() ?: 0, isActive = true)
            joinMeeting()
        } catch (e: Exception) {
            Log.e(TAG, "Error creating RTC engine: ${e.message}")
        }
    }

    fun joinMeeting() {
        val options = ChannelMediaOptions().apply {
            clientRoleType = 1
            channelProfile = 1
            publishCameraTrack = true
        }

        rtcEngine?.joinChannel(
            videoConfig.token,
            videoConfig.channelName,
            videoConfig.userId?.toInt() ?: 0,
            options
        )
    }

    fun toggleVideo() {
        isVideoOn.value = !isVideoOn.value
        rtcEngine?.muteLocalVideoStream(!isVideoOn.value)
        updateVideoView(videoConfig.userId?.toInt() ?: 0, isVideoOn.value)
    }

    fun toggleMic() {
        isMicOn.value = !isMicOn.value
        rtcEngine?.muteLocalAudioStream(!isMicOn.value)
    }

    fun toggleViewMode() {
        viewMode.value = if (viewMode.value == ViewMode.GRID) ViewMode.SPOTLIGHT else ViewMode.GRID
    }

    fun destroyEngine() {
        rtcEngine?.apply {
            stopPreview()
            disableVideo()
            disableAudio()
            leaveChannel()
        }
        RtcEngine.destroy()
        rtcEngine = null
        Log.d(TAG, "RTC Engine destroyed")
    }

    private fun addVideoView(uid: Int, isActive: Boolean) {
        activeVideoViews.add(VideoView(uid, isActive))
    }

    private fun updateVideoView(uid: Int, isActive: Boolean) {
        val index = activeVideoViews.indexOfFirst { it.uid == uid }
        if (index != -1) {
            activeVideoViews[index] = VideoView(uid, isActive)
        }
    }

    private fun resetSpotlight() {
        showSpotlight.value = false
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            showSpotlight.value = true
        }
    }

    private val sessionEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.d(TAG, "User joined: $uid")
            addVideoView(uid, isActive = true)
        }

        override fun onUserMuteVideo(uid: Int, muted: Boolean) {
            updateVideoView(uid, !muted)
        }

        override fun onAudioVolumeIndication(speakers: Array<out AudioVolumeInfo>?, totalVolume: Int) {
            speakers?.forEach { speaker ->
                if (speaker.volume > 100) {
                    val newSpeakerUid = if (speaker.uid == 0) videoConfig.userId ?: "" else speaker.uid.toString()
                    if (activeSpeakerUid.value != newSpeakerUid) {
                        resetSpotlight()
                        activeSpeakerUid.value = newSpeakerUid
                    }
                }
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.d(TAG, "User offline: $uid, Reason: $reason")
            activeVideoViews.removeIf { it.uid == uid }
        }
    }

    companion object {
        const val TAG = "VideoServiceVM"
    }
}
