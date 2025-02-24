package com.example.tsunotintime.data.models

data class ReasonModel(
    val id: String,
    val images:List<String>?,
    val dateFrom: String,
    val dateTo: String,
    val description: String?,
    val reasonType: ReasonType
)
