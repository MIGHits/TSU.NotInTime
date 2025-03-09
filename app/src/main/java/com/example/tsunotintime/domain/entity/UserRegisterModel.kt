package com.example.tsunotintime.domain.entity

data class UserRegisterModel(
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val email: String,
    val password: String
)
