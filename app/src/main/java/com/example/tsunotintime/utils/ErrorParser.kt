package com.example.tsunotintime.utils

import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.R
import com.example.tsunotintime.domain.entity.ResponseModel
import com.google.gson.Gson
import retrofit2.Response

object ErrorParser {
    fun parseErrorMessage(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string() ?: return instance.getString(R.string.unknown_error)

            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody, ResponseModel::class.java)

            errorResponse.Message ?: instance.getString(R.string.unknown_error)
        } catch (e: Exception) {
            instance.getString(R.string.error_fetch_failed)
        }
    }
}