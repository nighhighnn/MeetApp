package com.example.meetapp.data.remote

import com.example.meetapp.data.remote.dto.TokenResponse
import com.example.meetapp.data.remote.dto.UserNameResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MeetApi {

    @GET("getToken")
    suspend fun getToken(
        @Query("userId") userId: String,
        @Query("channelName") channelName: String
    ): TokenResponse

    @GET("getUserName")
    suspend fun getUserName(
        @Query("uid") uid: String,
    ): UserNameResponse
}