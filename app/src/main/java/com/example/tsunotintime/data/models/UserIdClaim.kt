package com.example.tsunotintime.data.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserIdClaim(
    @SerialName("user_id") val userId: String
)
