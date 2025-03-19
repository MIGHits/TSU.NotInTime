package com.example.tsunotintime.presentation.state

sealed class ProfileEvent {
    data class EnteredPassword(val value: String) : ProfileEvent()
    data object ButtonClick : ProfileEvent()
    data object Logout : ProfileEvent()
}