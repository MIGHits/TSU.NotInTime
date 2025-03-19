package com.example.tsunotintime.data.repository

import com.example.tsunotintime.common.Constant.EMPTY_RESULT
import com.example.tsunotintime.data.remote.UserService
import com.example.tsunotintime.data.storage.TokenStorage
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.domain.entity.UserRegisterModel
import com.example.tsunotintime.domain.repository.AuthRepository
import com.example.tsunotintime.utils.ErrorParser.parseErrorMessage
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val userService: UserService,
    private val tokenStorage: TokenStorage
) : AuthRepository {
    override suspend fun register(credentials: UserRegisterModel) {

        val response = userService.register(credentials)
        if (response.isSuccessful) {
            response.body()?.accessToken?.let { tokenStorage.saveAccessToken(it) }
            response.body()?.refreshToken?.let { tokenStorage.saveRefreshToken(it) }
        } else {
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }

    override suspend fun login(credentials: LoginCredentialsModel) {
        val response = userService.login(credentials)
        if (response.isSuccessful) {
            response.body()?.accessToken?.let { tokenStorage.saveAccessToken(it) }
            response.body()?.refreshToken?.let { tokenStorage.saveRefreshToken(it) }
        } else {
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }

    override suspend fun logout() {
        val response = userService.logout()
        if (response.isSuccessful) {
            tokenStorage.removeToken()
        } else {
            val errorMessage = parseErrorMessage(response)
            throw HttpException(response).initCause(Throwable(errorMessage))
        }
    }

    override fun isUserLogged(): Boolean {
        return tokenStorage.getAccessToken() != EMPTY_RESULT
    }
}