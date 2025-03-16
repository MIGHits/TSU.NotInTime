package com.example.tsunotintime.di

import com.example.tsunotintime.presentation.viewModel.AddRequestViewModel
import com.example.tsunotintime.presentation.viewModel.AuthViewModel
import com.example.tsunotintime.presentation.viewModel.LoginViewModel
import com.example.tsunotintime.presentation.viewModel.ProfileViewModel
import com.example.tsunotintime.presentation.viewModel.RegisterViewModel
import com.example.tsunotintime.presentation.viewModel.RequestViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<LoginViewModel> {
        LoginViewModel(
            emailValidationUseCase = get(),
            passwordValidationUseCase = get(),
            loginUseCase = get()
        )
    }
    viewModel<RegisterViewModel> {
        RegisterViewModel(
            emailUseCase = get(),
            passwordUseCase = get(),
            confirmPasswordUseCase = get(),
            registrationFieldUseCase = get(),
            registerUseCase = get()
        )
    }
    viewModel<ProfileViewModel> {
        ProfileViewModel(
            validatePasswordUseCase = get(),
            getProfileUseCase = get(),
            updatePasswordUseCase = get(),
            logoutUseCase = get(),
            tokenStorage = get()
        )
    }
    viewModel<RequestViewModel> {
        RequestViewModel(
            getUserRequestsUseCase = get(),
            getRequestUseCase = get(),
            requestEditUseCase = get(),
            tokenStorage = get()
        )
    }
    viewModel<AddRequestViewModel> {
        AddRequestViewModel(addRequestUseCase = get(), tokenStorage = get())
    }
    viewModel<AuthViewModel> {
        AuthViewModel(
            isUserAuthorizedUseCase = get(),
            tokenStorage = get()
        )
    }
}