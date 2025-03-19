package com.example.tsunotintime.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel(
    @SerialName("Status") val Status: String?,
    @SerialName("Message") val Message: String?
)
