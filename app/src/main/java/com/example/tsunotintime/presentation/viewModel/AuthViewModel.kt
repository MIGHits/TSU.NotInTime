package com.example.tsunotintime.presentation.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.tsunotintime.common.Constant.ACCESS_TOKEN_KEY
import com.example.tsunotintime.data.storage.TokenStorage
import com.example.tsunotintime.domain.usecase.IsUserAuthorizedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    tokenStorage: TokenStorage
) : ViewModel() {
    private val _tokenState = MutableStateFlow(false)
    val tokenState: StateFlow<Boolean> = _tokenState
    private val storageListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == ACCESS_TOKEN_KEY) {
            _tokenState.value = isUserAuthorizedUseCase()
        }
    }

    init {
        tokenStorage.observeTokenState(storageListener)
        _tokenState.value = isUserAuthorizedUseCase()
    }
}