package com.example.tsunotintime.di

import com.example.tsunotintime.domain.usecase.ConfirmPasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidateEmailUseCase
import com.example.tsunotintime.domain.usecase.ValidatePasswordUseCase
import com.example.tsunotintime.domain.usecase.ValidateRegistrationFieldUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<ValidateEmailUseCase> { ValidateEmailUseCase(repository = get()) }
    factory<ValidatePasswordUseCase> { ValidatePasswordUseCase(repository = get()) }
    factory<ConfirmPasswordUseCase> { ConfirmPasswordUseCase(repository = get()) }
    factory<ValidateRegistrationFieldUseCase> { ValidateRegistrationFieldUseCase(repository = get()) }
}