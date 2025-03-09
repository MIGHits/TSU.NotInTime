package com.example.tsunotintime.domain.usecase

import com.example.tsunotintime.domain.repository.ValidationRepository
import com.example.tsunotintime.presentation.state.ValidationResult

class ValidateRegistrationFieldUseCase(private val repository: ValidationRepository) {
    operator fun invoke(inputValue: String, required: Boolean): ValidationResult {
        return repository.validateInput(inputValue, required)
    }
}