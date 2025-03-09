package com.example.tsunotintime.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.common.Constant.ACCESS_TOKEN_KEY
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.common.Constant.PREFS_NAME
import com.example.tsunotintime.common.Constant.REFRESH_TOKEN_KEY

object PrivateTokenStorage : TokenStorage {
    private val context = instance
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun getAccessToken(): String {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, EMPTY_RESULT).toString()
    }

    override fun saveAccessToken(token: String) {
        editor.putString(ACCESS_TOKEN_KEY, token)
    }

    override fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, EMPTY_RESULT).toString()
    }

    override fun saveRefreshToken(token: String) {
        editor.putString(REFRESH_TOKEN_KEY, token)
    }

    override fun removeToken() {
        editor.clear().apply()
    }
}