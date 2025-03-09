package com.example.tsunotintime.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tsunotintime.domain.usecase.ConfirmPasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidateEmailUseCase
import com.example.tsunotintime.domain.usecase.ValidatePasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidateRegistrationFieldUseCase
import com.example.tsunotintime.presentation.state.InputType
import com.example.tsunotintime.presentation.state.RegisterCredentialsState
import com.example.tsunotintime.presentation.state.RegisterEvent
import com.example.tsunotintime.presentation.state.ValidationResult

class RegisterViewModel(
    private val emailUseCase: ValidateEmailUseCase,
    private val passwordUseCase: ValidatePasswordUseCase,
    private val confirmPasswordUseCase: ConfirmPasswordUseCase,
    private val registrationFieldUseCase: ValidateRegistrationFieldUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf(RegisterCredentialsState(isValid = false))
    val state: State<RegisterCredentialsState> = _state

    fun createEvent(event: RegisterEvent) {
        onEvent(event)
    }

    private fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredName -> updateState { it.copy(name = it.name.copy(text = event.value)) }
            is RegisterEvent.EnteredLastName -> updateState {
                it.copy(
                    lastName = it.lastName.copy(
                        text = event.value
                    )
                )
            }

            is RegisterEvent.EnteredMiddleName -> updateState {
                it.copy(
                    middleName = it.middleName.copy(
                        text = event.value
                    )
                )
            }

            is RegisterEvent.EnteredEmail -> updateState { it.copy(email = it.email.copy(text = event.value)) }
            is RegisterEvent.EnteredPassword -> updateState {
                it.copy(
                    password = it.password.copy(
                        text = event.value
                    )
                )
            }

            is RegisterEvent.EnteredPasswordConfirmation -> updateState {
                it.copy(
                    passwordConfirmation = it.passwordConfirmation.copy(text = event.value)
                )
            }

            is RegisterEvent.ButtonClick -> {}
            is RegisterEvent.FormChange -> handleFormChange(event.focusField)
        }
    }

    private fun handleFormChange(focusField: InputType) {

        when (focusField) {
            InputType.EMAIL -> validateAndUpdateField(
                focusField,
                state.value.email.text
            ) { result ->
                copy(
                    email = email.copy(
                        isValid = result.result,
                        errorMessage = result.errorMessage,
                        isInitialState = false
                    )
                )
            }

            InputType.PASSWORD -> {
                validateAndUpdateField(focusField, state.value.password.text) { result ->
                    copy(
                        password = password.copy(
                            isValid = result.result,
                            errorMessage = result.errorMessage,
                            isInitialState = false
                        )
                    )
                }
                validateAndUpdateField(
                    InputType.PASSWORD_CONFIRMATION,
                    state.value.passwordConfirmation.text
                ) { result ->
                    copy(
                        passwordConfirmation = passwordConfirmation.copy(
                            isValid = result.result,
                            errorMessage = result.errorMessage,
                            isInitialState = false
                        )
                    )
                }
            }

            InputType.PASSWORD_CONFIRMATION -> validateAndUpdateField(
                focusField,
                state.value.passwordConfirmation.text
            ) { result ->
                copy(
                    passwordConfirmation = passwordConfirmation.copy(
                        isValid = result.result,
                        errorMessage = result.errorMessage,
                        isInitialState = false
                    )
                )
            }

            else -> {
                val fieldState = when (focusField) {
                    InputType.NAME -> state.value.name
                    InputType.LAST_NAME -> state.value.lastName
                    InputType.MIDDLE_NAME -> state.value.middleName
                    else -> null
                }

                fieldState?.let { field ->
                    validateAndUpdateField(focusField, field.text) { result ->
                        when (focusField) {
                            InputType.NAME -> copy(
                                name = name.copy(
                                    isValid = result.result,
                                    errorMessage = result.errorMessage,
                                    isInitialState = false
                                )
                            )

                            InputType.LAST_NAME -> copy(
                                lastName = lastName.copy(
                                    isValid = result.result,
                                    errorMessage = result.errorMessage,
                                    isInitialState = false
                                )
                            )

                            InputType.MIDDLE_NAME -> copy(
                                middleName = middleName.copy(
                                    isValid = result.result,
                                    errorMessage = result.errorMessage,
                                    isInitialState = false
                                )
                            )

                            else -> this
                        }
                    }
                }
            }
        }
        updateState { it.copy(isValid = formIsValid()) }
    }

    private fun validateAndUpdateField(
        inputType: InputType,
        value: String,
        update: RegisterCredentialsState.(ValidationResult) -> RegisterCredentialsState
    ) {
        val result = validateInput(value, inputType)
        updateState { it.update(result) }
    }

    private fun formIsValid(): Boolean {

        if (_state.value.middleName.text.isEmpty()) {
            _state.value =
                _state.value.copy(
                    middleName = _state.value.middleName.copy(isValid = true)
                )
        }

        return with(_state.value) {
            name.isValid &&
                    lastName.isValid &&
                    middleName.isValid &&
                    email.isValid &&
                    password.isValid &&
                    passwordConfirmation.isValid
        }
    }

    private fun updateState(update: (RegisterCredentialsState) -> RegisterCredentialsState) {
        _state.value = update(state.value)
    }

    private fun validateInput(inputValue: String, inputType: InputType): ValidationResult {
        return when (inputType) {
            InputType.EMAIL -> emailUseCase(inputValue)
            InputType.PASSWORD -> passwordUseCase(inputValue)
            InputType.PASSWORD_CONFIRMATION -> confirmPasswordUseCase(
                _state.value.password.text,
                inputValue
            )

            else -> registrationFieldUseCase(
                inputValue,
                (inputType in listOf(InputType.NAME, InputType.LAST_NAME))
            )
        }
    }
}
