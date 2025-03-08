package com.example.tsunotintime.data.repository

import android.text.TextUtils
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.R
import com.example.tsunotintime.domain.repository.ValidationRepository
import com.example.tsunotintime.presentation.state.ValidationResult

class ValidationRepositoryImpl : ValidationRepository {
    override fun validatePassword(password: String): ValidationResult {
        if (password.length <= 7) {
            return ValidationResult(
                result = false,
                errorMessage = instance.getString(R.string.minimal_password_length)
            )
        }
        return if (!password.contains(
                Regex(
                    "^(?=.*\\d).+\$"
                )
            )
        ) {
            ValidationResult(
                result = false,
                errorMessage = instance.getString(R.string.password_digit_rule)
            )
        } else {
            ValidationResult(result = true, errorMessage = null)
        }
    }

    override fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(
                email
            ).matches(), instance.getString(R.string.wrong_email)
        )
    }

    override fun validateInput(inputValue: String, required: Boolean): ValidationResult {
        if (inputValue.isEmpty() && required || !inputValue.contains(Regex("^[a-zA-Zа-яА-Я-]*\$"))) {
            return ValidationResult(
                result = false,
                errorMessage = instance.getString(R.string.wrong_value)
            )
        }
        return ValidationResult(result = true, errorMessage = null)
    }

    override fun validatePasswordConfirmation(
        password: String,
        passwordConfirmation: String
    ): ValidationResult {

        return if (password == passwordConfirmation) {
            ValidationResult(result = true, errorMessage = null)
        } else {
            ValidationResult(
                result = false,
                errorMessage = instance.getString(R.string.fail_password_confirm)
            )
        }
    }
}