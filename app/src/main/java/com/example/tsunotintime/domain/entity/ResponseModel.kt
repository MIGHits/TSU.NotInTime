package com.example.tsunotintime.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel(
    @SerialName("Status") val status: String?,
    @SerialName("Message") val message: String?
)
