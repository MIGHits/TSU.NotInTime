package com.example.tsunotintime.presentation.state

sealed class RegisterEvent {
    data class EnteredName(val value: String) : RegisterEvent()
    data class EnteredLastName(val value: String) : RegisterEvent()
    data class EnteredMiddleName(val value: String) : RegisterEvent()
    data class EnteredEmail(val value: String) : RegisterEvent()
    data class EnteredPasswordConfirmation(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    data class FormChange(val focusField: InputType) : RegisterEvent()
    data object ButtonClick : RegisterEvent()
}