package com.example.tsunotintime.data.repository

import com.example.tsunotintime.data.remote.AuthService
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.UserRegisterModel
import com.example.tsunotintime.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun register(credentials: UserRegisterModel) {
        authService.register(credentials)
    }

    override suspend fun login(credentials: LoginCredentialsModel) {
        authService.login(credentials)
    }

    override suspend fun logout() {

    }
}