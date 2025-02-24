package com.example.tsunotintime.data.models

data class RequestShortModel(
    val id: String,
    val createTime: String,
    val reasonId: String,
    val status: RequestStatus,
    val userType: UserType,
    val absenceDateFrom: String,
    val absenceDateTo: String
)
