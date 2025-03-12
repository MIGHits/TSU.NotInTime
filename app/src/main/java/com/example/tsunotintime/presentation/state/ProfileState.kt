package com.example.tsunotintime.presentation.state

import com.example.tsunotintime.common.Constant.EMPTY_RESULT

data class ProfileState(
    val name: String = EMPTY_RESULT,
    val middleName: String = EMPTY_RESULT,
    val lastName: String = EMPTY_RESULT,
    val email: String = EMPTY_RESULT,
    val status: String = EMPTY_RESULT,
    val newPassword: InputState = InputState(type = InputType.PASSWORD),
    val isValid: Boolean = false
)