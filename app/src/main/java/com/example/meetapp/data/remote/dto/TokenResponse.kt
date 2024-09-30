package com.example.meetapp.data.remote.dto

data class TokenResponse(
    val appId: String,
    val tokens: Tokens,
    val uid: String
)
