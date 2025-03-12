package com.example.tsunotintime.data.repository

import com.example.tsunotintime.data.remote.UserService
import com.example.tsunotintime.data.storage.PrivateTokenStorage
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.entity.UserRegisterModel
import com.example.tsunotintime.domain.repository.AuthRepository
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

class AuthRepositoryImpl(private val userService: UserService) : AuthRepository {
    override suspend fun register(credentials: UserRegisterModel) {

        val response = userService.register(credentials)
        if (response.isSuccessful) {
            response.body()?.accessToken?.let { PrivateTokenStorage.saveAccessToken(it) }
            response.body()?.refreshToken?.let { PrivateTokenStorage.saveRefreshToken(it) }
        } else {
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }

    private fun parseErrorMessage(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            if (!errorBody.isNullOrEmpty()) {
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ResponseModel::class.java)
                errorResponse.message.toString()
            } else {
                "Неизвестная ошибка"
            }
        } catch (e: Exception) {
            "Не удалось извлечь сообщение об ошибке"
        }
    }

    override suspend fun login(credentials: LoginCredentialsModel) {
        val response = userService.login(credentials)
        if (response.isSuccessful) {
            response.body()?.accessToken?.let { PrivateTokenStorage.saveAccessToken(it) }
            response.body()?.refreshToken?.let { PrivateTokenStorage.saveRefreshToken(it) }
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun logout() {
        val response = userService.logout()
        if (response.isSuccessful) {
            PrivateTokenStorage.removeToken()
        } else {
            throw HttpException(response)
        }
    }
}