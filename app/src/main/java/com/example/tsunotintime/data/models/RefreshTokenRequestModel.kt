package com.example.tsunotintime.data.models

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequestModel(
    val userId:String,
    val refreshToken:String
)
