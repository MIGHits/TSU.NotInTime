package com.example.tsunotintime.presentation.state

sealed class LoginEvent {
    data class EnteredEmail(val value: String) : LoginEvent()
    data class EnteredPassword(val value: String) : LoginEvent()
    data class FormChange(val focusField: InputType) : LoginEvent()
    data object ButtonClick : LoginEvent()
}