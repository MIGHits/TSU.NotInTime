package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.repository.ProfileRepository

class UpdateUserPasswordUseCase(
    private val repository: ProfileRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(newPassword: String): Result<ResponseModel?> {
        try {
            val response = repository.updatePassword(newPassword)
            return Result.Success(response)
        } catch (throwable: Throwable) {
            return Result.Error(errorHandler.getError(throwable))
        }
    }
}