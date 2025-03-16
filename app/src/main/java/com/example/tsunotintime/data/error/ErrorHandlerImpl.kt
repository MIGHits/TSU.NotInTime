package com.example.tsunotintime.data.error

import com.example.tsunotintime.domain.entity.ErrorEntity
import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.error.ErrorHandler
import com.google.gson.Gson
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.UnknownHostException

class ErrorHandlerImpl : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is UnknownHostException -> ErrorEntity.Network(throwable.message)
            is ConnectException -> ErrorEntity.Connection(throwable.message)
            is IOException -> ErrorEntity.Network(throwable.message)
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.BadRequest(throwable.cause?.message)
                    HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> ErrorEntity.Network(throwable.cause?.message)
                    HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorEntity.NonAuthorized(throwable.cause?.message)
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound(throwable.cause?.message)
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable(throwable.cause?.message)
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied(throwable.cause?.message)
                    else -> ErrorEntity.Unknown(throwable.cause?.message)
                }
            }

            else -> ErrorEntity.Unknown(throwable.message)
        }
    }
}