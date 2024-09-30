package com.example.meetapp.data.repository

import com.example.meetapp.data.remote.MeetApi
import com.example.meetapp.data.remote.dto.TokenResponse
import com.example.meetapp.data.remote.dto.UserNameResponse
import com.example.meetapp.domain.repository.MeetRepository
import javax.inject.Inject

class MeetRepositoryImpl @Inject constructor(
    private val api: MeetApi
): MeetRepository {

    override suspend fun getToken(userId: String, channelName: String): TokenResponse {
        return api.getToken(
            userId,
            channelName
        )
    }

    override suspend fun getUsername(uid: String): UserNameResponse {
        return api.getUserName(
            uid = uid
        )
    }

}