package com.example.meetapp.Presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.meetapp.Presentation.Components.Common.TextInputWrapper
import com.example.meetapp.Presentation.Screen
import com.example.meetapp.data.UserData
import com.example.meetapp.data.services.videoService.VideoServiceVM


@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    videoServiceVM: VideoServiceVM = hiltViewModel()
) {

    val context = LocalContext.current

    var userName by rememberSaveable {
        mutableStateOf("a")
    }

    var channel by rememberSaveable {
        mutableStateOf("a")
    }

    val loginState = viewModel.loginState.value

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        TextInputWrapper(
            text = userName,
            label = "Username",
        ) {
            userName = it
        }

        TextInputWrapper(
            text = channel,
            label = "Channel",
        ) {
            channel = it
        }

        Button(onClick = {
            viewModel.onSubmitPress{
                UserData.updateUserData(channel, it?.appId, it?.uid, it?.tokens?.rtcToken)
                navController.navigate(route = Screen.MeetScreen.route)
            }
        }) {
            if (loginState.isLoading == true) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Submit")
            }
        }
    }
}