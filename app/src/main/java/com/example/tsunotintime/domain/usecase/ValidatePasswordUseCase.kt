package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.repository.ValidationRepository
import com.example.tsunotintime.presentation.state.ValidationResult

class ValidatePasswordUseCase(private val repository: ValidationRepository) {
    operator fun invoke(password: String): ValidationResult {
        return repository.validatePassword(password)
    }
}