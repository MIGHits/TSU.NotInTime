package com.example.tsunotintime.data.models

data class RequestShortModel(
    val id: String,
    val createTime: String,
    val username: String?,
    val status: RequestStatus,
    val absenceDateFrom: String,
    val absenceDateTo: String
)
