package com.example.tsunotintime.presentation.state

data class RegisterCredentialsState(
    val name: InputState = InputState(type = InputType.NAME),
    val lastName: InputState = InputState(type = InputType.LAST_NAME),
    val middleName: InputState = InputState(type = InputType.MIDDLE_NAME),
    val email: InputState = InputState(type = InputType.EMAIL),
    val password: InputState = InputState(type = InputType.PASSWORD),
    val passwordConfirmation: InputState = InputState(type = InputType.PASSWORD_CONFIRMATION),
    val isValid: Boolean
)