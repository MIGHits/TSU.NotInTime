package com.example.tsunotintime.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsunotintime.common.Constant.CONNECTION_ERROR
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.common.Constant.NETWORK_ERROR
import com.example.tsunotintime.domain.entity.ErrorEntity
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.usecase.LoginUseCase
import com.example.tsunotintime.domain.usecase.ValidateEmailUseCase
import com.example.tsunotintime.domain.usecase.ValidatePasswordUseCase
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.LoginCredentialsState
import com.example.tsunotintime.presentation.state.LoginEvent
import com.example.tsunotintime.presentation.state.ScreenState
import com.example.tsunotintime.presentation.state.ValidationResult
import kotlinx.coroutines.launch

class LoginViewModel(
    private val emailValidationUseCase: ValidateEmailUseCase,
    private val passwordValidationUseCase: ValidatePasswordUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(ScreenState(currentState = FetchDataState.Initial))
    val screenState: State<ScreenState> = _screenState

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
                                isInitialState = false
                            ),
                        )
                    }

                    InputType.PASSWORD -> {
                        val passwordValid =
                            validateInput(state.value.password.text, InputType.PASSWORD)
                        _state.value = state.value.copy(
                            password = state.value.password.copy(
                                isValid = passwordValid.result,
                                errorMessage = passwordValid.errorMessage,
                                isInitialState = false
                            ),
                        )
                    }

                    else -> {}
                }
                _state.value =
                    _state.value.copy(
                        isValid = validateForm()
                    )
            }

            is LoginEvent.ButtonClick -> {
                login()
            }
        }
    }

    private fun validateForm(): Boolean {
        return _state.value.email.isValid &&
                _state.value.password.isValid
    }


    private fun login() {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Loading)
        viewModelScope.launch {
            val response = loginUseCase(
                LoginCredentialsModel(
                    _state.value.email.text,
                    _state.value.password.text
                )
            )
            when (response) {
                is Result.Success -> {
                    _screenState.value =
                        _screenState.value.copy(currentState = FetchDataState.Success)
                }

                is Result.Error -> {
                    var errorMessage = EMPTY_RESULT

                    when (response.error) {
                        is ErrorEntity.Network -> errorMessage = CONNECTION_ERROR

                        is ErrorEntity.AccessDenied -> errorMessage =
                            response.error.errorMessage.toString()

                        is ErrorEntity.BadRequest -> errorMessage =
                            response.error.errorMessage.toString()

                        is ErrorEntity.NotFound -> errorMessage =
                            response.error.errorMessage.toString()

                        is ErrorEntity.ServiceUnavailable -> errorMessage =
                            response.error.errorMessage.toString()

                        is ErrorEntity.Unknown -> errorMessage =
                            response.error.errorMessage.toString()

                        is ErrorEntity.Connection -> errorMessage = NETWORK_ERROR
                    }
                    _screenState.value =
                        _screenState.value.copy(currentState = FetchDataState.Error(errorMessage))
                }
            }
        }
    }

    private fun validateInput(inputValue: String, inputType: InputType): ValidationResult {
        return when (inputType) {
            InputType.EMAIL -> {
                emailValidationUseCase(inputValue)
            }

            InputType.PASSWORD -> {
                passwordValidationUseCase(inputValue)
            }

            else -> ValidationResult(true, null)
        }
    }
}