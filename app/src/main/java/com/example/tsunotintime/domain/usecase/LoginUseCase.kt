package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(credentials: LoginCredentialsModel) {
        repository.login(credentials)
    }
}