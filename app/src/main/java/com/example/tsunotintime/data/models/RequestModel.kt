package com.example.tsunotintime.data.models

data class RequestModel(
    val id: String,
    val createTime: String,
    val reasonId: String,
    val status: RequestStatus,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val userId: String,
    val checkerUsername: String,
    val images: List<String>?,
    val description: String,
    val absenceDateFrom: String,
    val absenceDateTo: String
)