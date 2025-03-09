package com.example.tsunotintime.di

import com.example.tsunotintime.data.error.ErrorHandlerImpl
import com.example.tsunotintime.data.repository.AuthRepositoryImpl
import com.example.tsunotintime.data.repository.ValidationRepositoryImpl
import com.example.tsunotintime.domain.error.ErrorHandler
import com.example.tsunotintime.domain.repository.AuthRepository
import com.example.tsunotintime.domain.repository.ValidationRepository
import org.koin.dsl.module

val dataModule = module {
    factory<ValidationRepository> {
        ValidationRepositoryImpl()
    }
    factory<AuthRepository> {
        AuthRepositoryImpl(authService = get())
    }
    single<ErrorHandler> {
        ErrorHandlerImpl()
    }
}