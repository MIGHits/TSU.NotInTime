package com.example.tsunotintime.data.models

data class RequestCreateModel (
    val absenceDateFrom:String,
    val absenceDateTo:String,
    val lessonName:String,
    val reasonId:String?
)