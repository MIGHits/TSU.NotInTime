package com.example.tsunotintime.presentation.viewModel

import android.text.TextUtils
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.LoginCredentialsState
import com.example.tsunotintime.presentation.state.LoginEvent
import com.example.tsunotintime.presentation.state.ValidationResult

class LoginViewModel : ViewModel() {
    private val _state = mutableStateOf(LoginCredentialsState(isValid = false))
    val state: State<LoginCredentialsState> = _state

    fun createEvent(event: LoginEvent) {
        onEvent(event)
    }

    private fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = state.value.email.copy(
                        text = event.value
                    )
                )
            }

            is LoginEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = state.value.password.copy(
                        text = event.value
                    )
                )
            }

            is LoginEvent.FormChange -> {
                when (event.focusField) {
                    InputType.EMAIL -> {
                        val emailValid = validateInput(state.value.email.text, InputType.EMAIL)
                        _state.value = state.value.copy(
                            email = state.value.email.copy(
                                isValid = emailValid.result,
                                errorMessage = emailValid.errorMessage,
                            ),
                        )
                    }

                    InputType.PASSWORD -> {
                        val passwordValid =
                            validateInput(state.value.password.text, InputType.PASSWORD)
                        _state.value = state.value.copy(
                            password = state.value.password.copy(
                                isValid = passwordValid.result,
                                errorMessage = passwordValid.errorMessage
                            ),
                        )
                        _state.value =
                            _state.value.copy(
                                isValid = _state.value.email.isValid &&
                                        _state.value.password.isValid
                            )
                    }
                }
            }

            is LoginEvent.ButtonClick -> {

            }
        }
    }


    private fun validateInput(inputValue: String, inputType: InputType): ValidationResult {
        return when (inputType) {
            InputType.EMAIL -> {
                ValidationResult(
                    !TextUtils.isEmpty(inputValue) && android.util.Patterns.EMAIL_ADDRESS.matcher(
                        inputValue
                    ).matches(), "Неправильный email"
                )
            }

            InputType.PASSWORD -> {
                ValidationResult(
                    !TextUtils.isEmpty(inputValue) && inputValue.length > 7 && inputValue.contains(
                        Regex(
                            "^(?=.*\\d).+\$"
                        )
                    ), "Неправильный пароль"
                )
            }
        }
    }
}