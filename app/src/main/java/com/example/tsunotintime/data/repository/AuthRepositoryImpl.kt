package com.example.tsunotintime.data.repository

import com.example.tsunotintime.data.remote.AuthService
import com.example.tsunotintime.data.storage.PrivateTokenStorage
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.UserRegisterModel
import com.example.tsunotintime.domain.repository.AuthRepository
import retrofit2.HttpException

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {
    override suspend fun register(credentials: UserRegisterModel) {

        val response = authService.register(credentials)
        if (response.isSuccessful) {
            response.body()?.accessToken?.let { PrivateTokenStorage.saveAccessToken(it) }
            response.body()?.refreshToken?.let { PrivateTokenStorage.saveRefreshToken(it) }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun login(credentials: LoginCredentialsModel) {
        val response = authService.login(credentials)
        if (response.isSuccessful) {
            response.body()?.accessToken?.let { PrivateTokenStorage.saveAccessToken(it) }
            response.body()?.refreshToken?.let { PrivateTokenStorage.saveRefreshToken(it) }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun logout() {
        val response = authService.logout()
        if (response.isSuccessful){
            PrivateTokenStorage.removeToken()
        }else{
            throw HttpException(response)
        }
    }
}