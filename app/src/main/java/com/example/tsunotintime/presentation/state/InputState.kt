package com.example.tsunotintime.presentation.state

import androidx.compose.ui.graphics.Color

enum class InputType {
    EMAIL,
    PASSWORD,
    PASSWORD_CONFIRMATION,
    NAME,
    LAST_NAME,
    MIDDLE_NAME
}

data class InputState(
    val text: String = "",
    val isValid: Boolean = true,
    val type: InputType,
    val errorMessage: String? = null
)