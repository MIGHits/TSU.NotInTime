package com.example.tsunotintime.di

import com.example.tsunotintime.presentation.viewModel.LoginViewModel
import com.example.tsunotintime.presentation.viewModel.ProfileViewModel
import com.example.tsunotintime.presentation.viewModel.RegisterViewModel
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
            registerUseCase = get(),
            logoutUseCase = get()
        )
    }
    viewModel<ProfileViewModel> {
        ProfileViewModel(
            validatePasswordUseCase = get(),
            getProfileUseCase = get(),
            updatePasswordUseCase = get(),
            logoutUseCase = get()
        )
    }
}