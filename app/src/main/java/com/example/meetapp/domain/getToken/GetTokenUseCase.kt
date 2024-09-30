package com.example.meetapp.domain.getToken

import com.example.meetapp.common.Resource
import com.example.meetapp.data.remote.dto.TokenResponse
import com.example.meetapp.domain.repository.MeetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.HttpRetryException
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: MeetRepository
) {

    operator fun invoke(): Flow<Resource<TokenResponse>> = flow {

        try {
            emit(Resource.Loading())
            val tokenResponse = repository.getToken(
                userId = "",
                channelName  = ""
            )
            emit(Resource.Success(tokenResponse))
        } catch (e: HttpRetryException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error in Loading"))
        }

    }

}