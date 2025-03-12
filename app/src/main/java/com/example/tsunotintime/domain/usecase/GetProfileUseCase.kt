package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.UserModel
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(): Result<UserModel?> {
        return try {
            val response = repository.getProfile()
            Result.Success(response)
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}