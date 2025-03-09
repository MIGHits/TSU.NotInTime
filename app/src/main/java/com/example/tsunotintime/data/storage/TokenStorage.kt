package com.example.tsunotintime.data.storage

import com.google.gson.stream.JsonToken

interface TokenStorage {
    fun getAccessToken(): String
    fun saveAccessToken(token: String)
    fun getRefreshToken(): String
    fun saveRefreshToken(token: String)
    fun removeToken()
}