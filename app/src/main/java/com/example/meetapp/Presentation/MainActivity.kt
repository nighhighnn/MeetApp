package com.example.meetapp.Presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meetapp.Presentation.login.LoginScreen
import com.example.meetapp.Presentation.meet.MeetScreen
import com.example.meetapp.ui.theme.MeetAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeetAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeContentPadding()
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ) {
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(
                            route = Screen.MeetScreen.route
                        ) {
                            MeetScreen()
                        }
                    }
                }
            }
        }
    }
}