package com.example.tsunotintime.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.tsunotintime.common.Constant.CONNECTION_ERROR
import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.common.Constant.NETWORK_ERROR
import com.example.tsunotintime.data.models.RequestShortModel
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.data.storage.PrivateTokenStorage
import com.example.tsunotintime.domain.entity.ErrorEntity
import com.example.tsunotintime.domain.usecase.GetRequestUseCase
import com.example.tsunotintime.domain.usecase.GetUserRequestsUseCase
import com.example.tsunotintime.presentation.state.FetchDataState
import com.example.tsunotintime.presentation.state.RequestState
import com.example.tsunotintime.presentation.state.ScreenState
import com.example.tsunotintime.domain.entity.Result
import com.example.tsunotintime.presentation.state.BadgeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestViewModel(
    private val getUserRequestsUseCase: GetUserRequestsUseCase,
    private val getRequestUseCase: GetRequestUseCase,
    private val imageLoader: ImageLoader
) : ViewModel() {
    private val _screenState =
        MutableStateFlow((ScreenState(currentState = FetchDataState.Initial)))
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _requestState = MutableStateFlow((RequestState()))
    val requestState: StateFlow<RequestState> = _requestState.asStateFlow()

    private val _cardDialogState =
        MutableStateFlow(ScreenState(currentState = FetchDataState.Initial))
    val cardDialogState: StateFlow<ScreenState> = _cardDialogState.asStateFlow()

    private val accessToken = PrivateTokenStorage.getAccessToken()

    private val decodedJWT: DecodedJWT = JWT.decode(accessToken)
    private val userId = decodedJWT.getClaim("user_id").asString()

    init {
        getRequests()
    }

    fun getImageLoader(): ImageLoader {
        return imageLoader
    }

    fun getRequests() {
        _screenState.value = _screenState.value.copy(currentState = FetchDataState.Loading)
        viewModelScope.launch {
            when (val response = getUserRequestsUseCase(userId)) {
                is Result.Success -> {
                    _requestState.value =
                        _requestState.value.copy(requests = response.data?.requestsList)
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

    fun getDetails(requestId: String) {
        _cardDialogState.value = _cardDialogState.value.copy(currentState = FetchDataState.Loading)
        viewModelScope.launch {
            when (val response = getRequestUseCase(requestId)) {
                is Result.Success -> {
                    _requestState.value = _requestState.value.copy(
                        requestDetails = _requestState.value.requestDetails.copy(requestModel = response.data)
                    )
                    _cardDialogState.value =
                        _cardDialogState.value.copy(currentState = FetchDataState.Success)
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
                    _cardDialogState.value =
                        _cardDialogState.value.copy(currentState = FetchDataState.Error(errorMessage))
                }
            }
        }
    }

    fun updateBadges(requests: List<RequestShortModel>) {
        val badgeState = BadgeState(
            confirmed = requests.count { it.status == RequestStatus.Confirmed },
            rejected = requests.count { it.status == RequestStatus.Rejected },
            pending = requests.count { it.status == RequestStatus.Checking }
        )

        _requestState.value = _requestState.value.copy(badgeState = badgeState)
    }

}