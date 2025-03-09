package com.example.tsunotintime.data.remote

import com.example.tsunotintime.data.models.RefreshTokenRequestModel
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.data.models.TokenResponseModel
import com.example.tsunotintime.domain.entity.UserRegisterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {
    @POST
    suspend fun register(@Body credentials: UserRegisterModel): TokenResponseModel

    @POST
    suspend fun login(@Body credentials: LoginCredentialsModel): TokenResponseModel

    @POST
    suspend fun refreshToken(@Body tokenResponseModel: RefreshTokenRequestModel): Response<TokenResponseModel>
}