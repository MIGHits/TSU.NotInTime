package com.example.tsunotintime.domain.entity

sealed class ErrorEntity {
    data class Network(val errorMessage: String?) : ErrorEntity()
    data class NotFound(val errorMessage: String?) : ErrorEntity()
    data class ServiceUnavailable(val errorMessage: String?) : ErrorEntity()
    data class AccessDenied(val errorMessage: String?) : ErrorEntity()
    data class BadRequest(val errorMessage: String?) : ErrorEntity()
    data class Unknown(val errorMessage: String?) : ErrorEntity()
}