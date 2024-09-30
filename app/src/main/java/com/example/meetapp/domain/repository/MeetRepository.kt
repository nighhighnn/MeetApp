package com.example.meetapp.domain.repository

import com.example.meetapp.data.remote.dto.TokenResponse
import com.example.meetapp.data.remote.dto.UserNameResponse

interface MeetRepository {

    suspend fun getToken(userId: String, channelName: String): TokenResponse

    suspend fun getUsername(uid: String): UserNameResponse

}