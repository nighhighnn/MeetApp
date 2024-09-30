package com.example.meetapp.domain.getUserName

import com.example.meetapp.common.Resource
import com.example.meetapp.data.remote.dto.UserNameResponse
import com.example.meetapp.domain.repository.MeetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.HttpRetryException
import javax.inject.Inject

class GetUserNameUseCase @Inject constructor(
    private val repository: MeetRepository
) {


    operator fun invoke(uid: String): Flow<Resource<UserNameResponse>> = flow {
        try {
            emit(Resource.Loading(null))
            val userNameResponse = repository.getUsername(uid)
            emit(Resource.Success(userNameResponse))
        } catch (e: HttpRetryException){
            emit(Resource.Error(e.localizedMessage))
        }
    }

}