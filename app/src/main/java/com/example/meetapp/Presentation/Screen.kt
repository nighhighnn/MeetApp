package com.example.meetapp.Presentation

sealed class Screen(val route: String) {

    object LoginScreen: Screen("login_screen")

    object MeetScreen: Screen("meet_screen")

}