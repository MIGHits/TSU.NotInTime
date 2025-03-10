package com.example.tsunotintime.presentation.state

sealed class ProfileEvent {
    data class EnteredPassword(val value: String) : ProfileEvent()
    data class FormChange(val focusField: InputType) : ProfileEvent()
    data object ButtonClick : ProfileEvent()
    data object Logout : ProfileEvent()
}