package com.example.tsunotintime.domain.error

import com.example.tsunotintime.domain.entity.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}