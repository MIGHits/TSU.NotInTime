package com.example.tsunotintime.di

import com.example.tsunotintime.domain.usecase.ConfirmPasswordUseCase
import com.example.tsunotintime.domain.usecase.GetProfileUseCase
import com.example.tsunotintime.domain.usecase.GetRequestUseCase
import com.example.tsunotintime.domain.usecase.GetUserRequestsUseCase
import com.example.tsunotintime.domain.usecase.LoginUseCase
import com.example.tsunotintime.domain.usecase.LogoutUseCase
import com.example.tsunotintime.domain.usecase.RegisterUseCase
import com.example.tsunotintime.domain.usecase.RequestEditUseCase
import com.example.tsunotintime.domain.usecase.UpdateUserPasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidateEmailUseCase
import com.example.tsunotintime.domain.usecase.ValidatePasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidateRegistrationFieldUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<ValidateEmailUseCase> { ValidateEmailUseCase(repository = get()) }
    factory<ValidatePasswordUseCase> { ValidatePasswordUseCase(repository = get()) }
    factory<ConfirmPasswordUseCase> { ConfirmPasswordUseCase(repository = get()) }
    factory<ValidateRegistrationFieldUseCase> { ValidateRegistrationFieldUseCase(repository = get()) }

    factory<LoginUseCase> { LoginUseCase(repository = get(), errorHandler = get()) }
    factory<RegisterUseCase> { RegisterUseCase(repository = get(), errorHandler = get()) }
    factory<LogoutUseCase> { LogoutUseCase(repository = get(), errorHandler = get()) }

    factory<GetProfileUseCase> { GetProfileUseCase(repository = get(), errorHandler = get()) }
    factory<UpdateUserPasswordUseCase> {
        UpdateUserPasswordUseCase(
            repository = get(),
            errorHandler = get()
        )
    }

    factory<GetRequestUseCase> { GetRequestUseCase(repository = get(), errorHandler = get()) }
    factory<GetUserRequestsUseCase> {
        GetUserRequestsUseCase(
            repository = get(),
            errorHandler = get()
        )
    }
    factory<RequestEditUseCase> { RequestEditUseCase(repository = get(), errorHandler = get()) }
}