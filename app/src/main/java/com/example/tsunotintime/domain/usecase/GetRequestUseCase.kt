package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.repository.RequestRepository

class GetRequestUseCase(
    private val repository: RequestRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(requestId: String): Result<RequestModel?> {
        return try {
            val response = repository.getRequest(requestId)
            Result.Success(response)
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}