package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.repository.ValidationRepository
import com.example.tsunotintime.presentation.state.ValidationResult

class ConfirmPasswordUseCase(private val repository: ValidationRepository) {
    operator fun invoke(password: String, passwordConfirmation: String): ValidationResult {
        return repository.validatePasswordConfirmation(password, passwordConfirmation)
    }
}