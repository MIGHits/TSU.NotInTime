package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}