package com.example.tsunotintime.data.storage

import android.content.SharedPreferences

interface TokenStorage {
    fun getAccessToken(): String
    fun saveAccessToken(token: String)
    fun getRefreshToken(): String
    fun saveRefreshToken(token: String)
    fun removeToken()
    fun observeTokenState(listener: SharedPreferences.OnSharedPreferenceChangeListener)
    fun unregisterTokenState(listener: SharedPreferences.OnSharedPreferenceChangeListener)
}