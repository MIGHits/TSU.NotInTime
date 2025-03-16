package com.example.tsunotintime.utils

import android.content.Context
import com.example.tsunotintime.R
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.presentation.state.ValidationResult
import com.example.tsunotintime.utils.DateTimeParser.formatIsoDateToDisplay

object DateValidator {
    fun validateDateTime(
        absenceDateFrom: String,
        absenceDateTo: String,
        context: Context
    ): ValidationResult {
        return try {
            if (absenceDateFrom >= absenceDateTo) {
                ValidationResult(
                    result = false,
                    errorMessage = context.getString(R.string.earlierDateError)
                )
            } else {
                ValidationResult(result = true, errorMessage = EMPTY_RESULT)
            }
        } catch (e: Exception) {
            ValidationResult(result = false, errorMessage = context.getString(R.string.date_error))
        }
    }
}