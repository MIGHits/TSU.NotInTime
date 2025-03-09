package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.entity.UserRegisterModel
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(credentials: UserRegisterModel): Result<Unit> {
        return try {
            val response = repository.register(credentials)
            Result.Success(response)
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}