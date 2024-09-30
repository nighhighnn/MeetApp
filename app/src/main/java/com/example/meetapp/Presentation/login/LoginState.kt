package com.example.meetapp.Presentation.login

import com.example.meetapp.data.remote.dto.TokenResponse
import com.example.meetapp.data.remote.dto.Tokens

data class LoginState(
    val isLoading: Boolean? = false,
    val tokenResponse: TokenResponse? = TokenResponse(
        uid = "",
        tokens = Tokens(
            rtcToken = "",
            rtmToken = ""
        ),
        appId = ""
    ),
    val error: Boolean? = false
)
