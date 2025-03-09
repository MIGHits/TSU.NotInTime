package com.example.tsunotintime.presentation.state

sealed class FetchDataState {
    data object Initial : FetchDataState()
    data object Loading : FetchDataState()
    data object Success : FetchDataState()
    data class Error(val message: String) : FetchDataState()
}