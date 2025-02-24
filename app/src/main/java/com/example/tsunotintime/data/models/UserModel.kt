package com.example.tsunotintime.data.models

data class UserModel(
    val id: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val email: String,
    val userType: UserType
)
