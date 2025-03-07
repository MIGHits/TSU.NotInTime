package com.example.tsunotintime.domain.repository

import com.example.tsunotintime.domain.usecase.ValidatePasswordUseCase
import com.example.tsunotintime.presentation.state.ValidationResult

interface ValidationRepository {
    fun validatePassword(password: String): ValidationResult
    fun validateEmail(email: String): ValidationResult
    fun validateInput(inputValue: String): ValidationResult
    fun validatePasswordConfirmation(password: String, passwordConfirmation: String):ValidationResult
}