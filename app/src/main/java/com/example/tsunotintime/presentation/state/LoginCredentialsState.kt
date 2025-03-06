package com.example.tsunotintime.presentation.state

data class LoginCredentialsState(
    val email: InputState = InputState(type = InputType.EMAIL),
    val password: InputState = InputState(type = InputType.PASSWORD),
    val isValid: Boolean
)