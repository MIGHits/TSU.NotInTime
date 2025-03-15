package com.example.tsunotintime.presentation.state

data class BadgeState(
    var confirmed: Int = 0,
    var rejected: Int = 0,
    var pending: Int = 0
)