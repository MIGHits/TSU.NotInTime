package com.example.tsunotintime.presentation.viewModel

import android.net.Uri
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsunotintime.AppContext.Companion.instance
import com.example.tsunotintime.common.Constant.CONNECTION_ERROR
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.common.Constant.NETWORK_ERROR
import com.example.tsunotintime.common.Constant.UNAUTHORIZED_ERROR
import com.example.tsunotintime.data.storage.PrivateTokenStorage
import com.example.tsunotintime.data.storage.TokenStorage
import com.example.tsunotintime.domain.entity.ErrorEntity
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.domain.usecase.AddRequestUseCase
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.RequestFormState
import com.example.tsunotintime.presentation.state.ScreenState
import com.example.tsunotintime.utils.DateValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddRequestViewModel(
    private val addRequestUseCase: AddRequestUseCase,
    private val tokenStorage: TokenStorage
) : ViewModel() {
    private val _screenState = MutableStateFlow(ScreenState(currentState = FetchDataState.Initial))
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _requestFormState = MutableStateFlow(RequestFormState())
    val requestFormState: StateFlow<RequestFormState> = _requestFormState.asStateFlow()

    private fun validateForm() {
        _requestFormState.value = _requestFormState.value.copy(
            isValid = DateValidator.validateDateTime(
                _requestFormState.value.absenceDateFrom,
                _requestFormState.value.absenceDateTo,
                instance
            ).result && _requestFormState.value.description.isNotEmpty()
        )
    }

    fun setAbsenceDateFrom(date: String) {
        _requestFormState.value = _requestFormState.value.copy(absenceDateFrom = date)
        validateForm()
    }

    fun setAbsenceDateTo(date: String) {
        _requestFormState.value = _requestFormState.value.copy(absenceDateTo = date)
        validateForm()
    }

    fun setDescription(description: String) {
        _requestFormState.value = _requestFormState.value.copy(description = description)
        validateForm()
    }

    fun setImages(images: List<Uri>) {
        _requestFormState.value = _requestFormState.value.copy(images = images)
    }

    fun toInitialState() {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Initial)
    }

    fun addRequest() {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Loading)
        viewModelScope.launch {
            when (val response = addRequestUseCase(
                _requestFormState.value.absenceDateFrom,
                _requestFormState.value.absenceDateTo,
                _requestFormState.value.description,
                _requestFormState.value.images
            )) {
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
                        is ErrorEntity.NonAuthorized -> {
                            errorMessage  = UNAUTHORIZED_ERROR
                            tokenStorage.removeToken()
                        }
                    }
                    _screenState.value =
                        _screenState.value.copy(currentState = FetchDataState.Error(errorMessage))
                }
            }
        }
    }
}