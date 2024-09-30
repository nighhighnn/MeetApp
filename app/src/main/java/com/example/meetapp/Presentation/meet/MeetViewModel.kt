package com.example.meetapp.Presentation.meet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetapp.common.Resource
import com.example.meetapp.domain.getUserName.GetUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MeetViewModel @Inject constructor(
    private val getUserNameUseCase: GetUserNameUseCase
): ViewModel() {

    private val _userNameState = mutableStateOf(MeetState())

    val userNameState get() = _userNameState.value

    init {
        onInit()
    }

    fun onInit() {
        getUserNameUseCase(uid = "").onEach {
            when(it) {
                is Resource.Loading -> {
                    _userNameState.value = MeetState(isLoading = true)
                }

                is Resource.Success -> {
                    _userNameState.value = MeetState(data = it.data)
                }

                is Resource.Error -> {
                    _userNameState.value = MeetState(error = it.message)
                }
            }
        }.launchIn(viewModelScope)
    }



}