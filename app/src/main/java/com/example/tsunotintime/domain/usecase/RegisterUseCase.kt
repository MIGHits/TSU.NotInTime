package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.entity.UserRegisterModel
import com.example.tsunotintime.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(credentials: UserRegisterModel) {
        repository.register(credentials)
    }
}