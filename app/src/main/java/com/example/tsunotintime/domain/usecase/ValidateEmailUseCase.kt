package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.repository.ValidationRepository
import com.example.tsunotintime.presentation.state.ValidationResult

class ValidateEmailUseCase(private val repository: ValidationRepository) {
    operator fun invoke(email: String): ValidationResult {
        return repository.validateEmail(email)
    }
}