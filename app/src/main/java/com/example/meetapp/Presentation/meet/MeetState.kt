package com.example.meetapp.Presentation.meet

import com.example.meetapp.data.remote.dto.UserNameResponse

data class MeetState(
    var isLoading: Boolean = false,
    var data: UserNameResponse? = UserNameResponse(""),
    var error: String? = ""
)