package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.repository.AuthRepository

class IsUserAuthorizedUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean {
        return repository.isUserLogged()
    }
}