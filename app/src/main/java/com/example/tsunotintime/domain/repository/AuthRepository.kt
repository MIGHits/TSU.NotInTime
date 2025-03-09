package com.example.tsunotintime.domain.repository

import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.UserRegisterModel

interface AuthRepository {
    suspend fun register(credentials: UserRegisterModel)
    suspend fun login(credentials: LoginCredentialsModel)
    suspend fun logout()
}