package com.example.meetapp.Presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetapp.common.Resource
import com.example.meetapp.data.remote.dto.TokenResponse
import com.example.meetapp.domain.getToken.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val getTokenUseCase: GetTokenUseCase
): ViewModel() {

    private val _loginState = mutableStateOf(LoginState())

    val loginState: State<LoginState> = _loginState

    fun onSubmitPress(onSuccess: (data: TokenResponse?) -> Unit) {
        getTokenUseCase().onEach {
            when(it) {
                is Resource.Loading -> {
                    _loginState.value = LoginState(isLoading = true)
                }
                is Resource.Success -> {
                    _loginState.value = LoginState(tokenResponse = it.data)
                    onSuccess(it.data)
                }
                is Resource.Error -> {
                    _loginState.value = LoginState(error = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}