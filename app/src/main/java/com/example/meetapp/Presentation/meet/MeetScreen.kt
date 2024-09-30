package com.example.meetapp.Presentation.meet

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.meetapp.R
import com.example.meetapp.data.UserData
import com.example.meetapp.data.services.videoService.VideoConfig
import com.example.meetapp.data.services.videoService.VideoServiceVM
import com.example.meetapp.data.services.videoService.ViewMode


@Composable
fun MeetScreen(
    videoServiceVM: VideoServiceVM = hiltViewModel(),
) {
    val context = LocalContext.current

    // Initialize video engine
    LaunchedEffect(UserData.getAppId()) {
        videoServiceVM.initializeEngine(
            context,
            VideoConfig(
                UserData.getUid(),
                UserData.getRtcToken(),
                UserData.getAppId(),
                UserData.getChannel()
            )
        )
    }

    // Cleanup on disposal
    DisposableEffect(UserData.getAppId()) {
        onDispose {
            videoServiceVM.destroyEngine()
        }
    }

    val activeViews = videoServiceVM.activeVideos
    val isSpotlightMode = videoServiceVM.currentViewMode == ViewMode.SPOTLIGHT
    val spotlightItem = activeViews.find { it.uid.toString() == videoServiceVM.activeSpeaker }

    Log.d("MeetScreen", "Active Views: ${activeViews.map { it.uid }}")

    // Toggle view mode button
    Button(onClick = { videoServiceVM.toggleViewMode() }) {
        Text(text = isSpotlightMode.toString())
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(if (isSpotlightMode) 2 else 1),
        modifier = Modifier.fillMaxSize()
    ) {
        items(activeViews.size) { index ->
            val currentView = activeViews[index]
            val isSpeaking = videoServiceVM.activeSpeaker == currentView.uid.toString()
            val borderColor = if (isSpeaking) Color.Yellow else Color.White
            val isActive = currentView.isVideoActive

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User name placeholder
                Text(text = "User $index", fontSize = 16.sp)

                // Display video or fallback image
                if (isActive && !(isSpeaking && isSpotlightMode)) {
                    AndroidView(
                        factory = { videoViewFactory(currentView.uid, videoServiceVM, context) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(3.dp, borderColor, RoundedCornerShape(10.dp))
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user_default_pic),
                        contentDescription = "Dummy User",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(3.dp, borderColor, RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }

    // Spotlight view
    if (isSpotlightMode && videoServiceVM.spotlightVisible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                spotlightItem?.let {
                    val borderColor = Color.Yellow
                    val uid = videoServiceVM.activeSpeaker

                    // Display spotlight video or fallback image
                    if (it.isVideoActive) {
                        Text(text = "Spotlight User", fontSize = 18.sp)
                        AndroidView(
                            factory = { videoViewFactory(uid.toInt(), videoServiceVM, context) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(3.dp, borderColor, RoundedCornerShape(10.dp))
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.user_default_pic),
                            contentDescription = "Dummy User $uid",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(3.dp, borderColor)
                        )
                    }
                }
            }
        }
    }
}

