package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository,
    private val errorHandler: ErrorHandler
) {
    suspend operator fun invoke(credentials: LoginCredentialsModel): Result<Unit> {
        try {
            val response = repository.login(credentials)
            return Result.Success(response)
        } catch (throwable: Throwable) {
            return Result.Error(errorHandler.getError(throwable))
        }
    }
}