package com.example.meetapp.Presentation.meet

import android.content.Context
import android.view.SurfaceView
import com.example.meetapp.data.services.videoService.VideoServiceVM
import io.agora.rtc2.video.VideoCanvas

fun videoViewFactory(item: Int?, videoServiceVM: VideoServiceVM, context: Context): SurfaceView {
    val surfaceView = SurfaceView(context)

    item?.let { uid ->
        val videoConfig = videoServiceVM.currVideoConfig
        videoConfig.let {
            val isSelf = videoConfig.userId == uid.toString()
            val videoCanvas = VideoCanvas(
                surfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )

            if (isSelf) {
                videoServiceVM.videoRtcEngine?.setupLocalVideo(videoCanvas)
            } else {
                videoServiceVM.videoRtcEngine?.setupRemoteVideo(videoCanvas)
            }
        }
    }

    return surfaceView
}