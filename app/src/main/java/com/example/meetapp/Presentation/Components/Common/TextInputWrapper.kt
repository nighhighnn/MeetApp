package com.example.meetapp.Presentation.Components.Common

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import java.lang.reflect.Modifier

@Composable
fun TextInputWrapper(text: String, label: String, onTextChange: (text: String) -> Unit) {
    TextField(
        value = text,
        label = {
            Text(text = label)
        },
        onValueChange = {
            onTextChange(it)
        },
    )
}