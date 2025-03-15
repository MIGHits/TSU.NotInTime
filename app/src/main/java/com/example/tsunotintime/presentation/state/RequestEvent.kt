package com.example.tsunotintime.presentation.state

sealed class RequestEvent {
    data class EnteredNewDescription(val value: String) : ProfileEvent()
    data class FormChange(val focusField: InputType) : ProfileEvent()
    data object ButtonClick : ProfileEvent()
}