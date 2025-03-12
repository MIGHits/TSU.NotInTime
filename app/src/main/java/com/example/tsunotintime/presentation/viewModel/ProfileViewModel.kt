package com.example.tsunotintime.presentation.viewModel

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.common.Constant.ADMIN
import com.example.tsunotintime.common.Constant.CONNECTION_ERROR
import com.example.tsunotintime.common.Constant.DEAN
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.common.Constant.NETWORK_ERROR
import com.example.tsunotintime.common.Constant.STUDENT
import com.example.tsunotintime.common.Constant.TEACHER
import com.example.tsunotintime.common.Constant.UNVERIFIED
import com.example.tsunotintime.data.models.UserType
import com.example.tsunotintime.domain.entity.ErrorEntity
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.usecase.GetProfileUseCase
import com.example.tsunotintime.domain.usecase.LogoutUseCase
import com.example.tsunotintime.domain.usecase.UpdateUserPasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidatePasswordUseCase
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.ProfileEvent
import com.example.tsunotintime.presentation.state.ProfileState
import com.example.tsunotintime.presentation.state.ScreenState
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val updatePasswordUseCase: UpdateUserPasswordUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(ScreenState(currentState = FetchDataState.Initial))
    val screenState: State<ScreenState> = _screenState

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    init {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Loading)
        getProfile()
    }

    fun createEvent(event: ProfileEvent) {
        onEvent(event)
    }

    private fun getProfile() {
        viewModelScope.launch {
            when (val response = getProfileUseCase()) {

                is Result.Success -> {
                    val data = response.data
                    _profileState.value =
                        _profileState.value.copy(
                            name = data?.firstName ?: EMPTY_RESULT,
                            lastName = data?.lastName ?: EMPTY_RESULT,
                            middleName = data?.middleName ?: EMPTY_RESULT,
                            email = data?.email ?: EMPTY_RESULT,
                            status = when (data?.userType) {
                                UserType.Student -> STUDENT
                                UserType.Dean -> DEAN
                                UserType.Admin -> ADMIN
                                UserType.Unverified -> UNVERIFIED
                                UserType.Teacher -> TEACHER
                                null -> UNVERIFIED
                            }
                        )
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

    private fun updatePassword() {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Loading)
        viewModelScope.launch {
            when (val response = updatePasswordUseCase(_profileState.value.newPassword.text)) {

                is Result.Success -> {
                    Toast.makeText(instance, response.data?.message, Toast.LENGTH_SHORT).show()
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

    private fun logout() {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Loading)
        viewModelScope.launch {
            when (val response = logoutUseCase()) {
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

    private fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.EnteredPassword -> {
                _profileState.value = _profileState.value.copy(
                    newPassword = _profileState.value.newPassword.copy(
                        text = event.value
                    )
                )
                val passwordValid =
                    validatePasswordUseCase(_profileState.value.newPassword.text)
                _profileState.value = _profileState.value.copy(
                    newPassword = _profileState.value.newPassword.copy(
                        isValid = passwordValid.result,
                        errorMessage = passwordValid.errorMessage,
                        isInitialState = false
                    ),
                )
                _profileState.value =
                    _profileState.value.copy(
                        isValid = _profileState.value.newPassword.isValid
                    )
            }

            is ProfileEvent.ButtonClick -> {
                updatePassword()
            }

            is ProfileEvent.Logout -> {
                logout()
            }

            is ProfileEvent.FormChange -> {}
        }
    }
}