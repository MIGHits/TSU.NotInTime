package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.RequestListModel
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.repository.RequestRepository

class GetUserRequestsUseCase(
    private val repository: RequestRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(userId: String): Result<RequestListModel?> {
        return try {
            val response = repository.getRequestList(userId)
            Result.Success(response)
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}
