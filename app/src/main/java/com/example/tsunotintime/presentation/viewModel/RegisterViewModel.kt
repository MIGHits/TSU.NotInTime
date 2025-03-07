package com.example.tsunotintime.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tsunotintime.presentation.state.RegisterCredentialsState
import com.example.tsunotintime.presentation.state.RegisterEvent

class RegisterViewModel : ViewModel() {
    private val _state = mutableStateOf(RegisterCredentialsState(isValid = false))
    val state: State<RegisterCredentialsState> get() = _state

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
            is RegisterEvent.FormChange -> {}
        }
    }

    private fun updateState(update: (RegisterCredentialsState) -> RegisterCredentialsState) {
        _state.value = update(state.value)
    }
}