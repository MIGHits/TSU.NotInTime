package com.example.tsunotintime.domain.entity

import com.example.tsunotintime.data.models.UserType

data class UserModel(
    val id: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String,
    val email: String,
    val userTypes: List<UserType>
)
