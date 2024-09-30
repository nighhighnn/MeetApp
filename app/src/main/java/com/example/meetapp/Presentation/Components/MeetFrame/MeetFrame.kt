package com.example.meetapp.Presentation.Components.MeetFrame

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MeetFrame(id: String, focusStateId: String, onClick: (id: String) -> Unit) {
    println("$id, $focusStateId")
    val textColor = if (id == focusStateId) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.tertiary
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            .clickable {
                onClick(id)
            }

    ) {
        Text(text = "hello", color = textColor)
    }
}