package com.example.tsunotintime.presentation.state

import androidx.compose.ui.graphics.Color

enum class InputType {
    EMAIL,
    PASSWORD
}

data class InputState(
    val text: String = "",
    val isValid: Boolean = true,
    val type: InputType,
    val errorMessage: String? = null
)