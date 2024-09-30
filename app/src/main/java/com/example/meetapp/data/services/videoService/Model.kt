package com.example.meetapp.data.services.videoService

data class VideoConfig(
    val userId: String?,
    val token: String?,
    val apiId: String?,
    val channelName: String?
)

data class VideoView(
    val uid: Int,
    val isVideoActive: Boolean
)