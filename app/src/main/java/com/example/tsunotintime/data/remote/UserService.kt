package com.example.tsunotintime.data.remote

import com.example.tsunotintime.common.URL.LOGIN_URL
import com.example.tsunotintime.common.URL.LOGOUT_URL
import com.example.tsunotintime.common.URL.PROFILE_URL
import com.example.tsunotintime.common.URL.REFRESH_URL
import com.example.tsunotintime.common.URL.REGISTER_URL
import com.example.tsunotintime.data.models.RefreshTokenRequestModel
import com.example.tsunotintime.domain.entity.ResponseModel
import com.example.tsunotintime.domain.entity.LoginCredentialsModel
import com.example.tsunotintime.data.models.TokenResponseModel
import com.example.tsunotintime.data.models.UserEditModel
import com.example.tsunotintime.domain.entity.UserModel
import com.example.tsunotintime.domain.entity.UserRegisterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface UserService {
    @POST(REGISTER_URL)
    suspend fun register(@Body credentials: UserRegisterModel): Response<TokenResponseModel>

    @POST(LOGIN_URL)
    suspend fun login(@Body credentials: LoginCredentialsModel): Response<TokenResponseModel>

    @POST(REFRESH_URL)
    suspend fun refreshToken(@Body tokenResponseModel: RefreshTokenRequestModel): Response<TokenResponseModel>

    @POST(LOGOUT_URL)
    suspend fun logout(): Response<ResponseModel>

    @GET(PROFILE_URL)
    suspend fun getProfile(): Response<UserModel>

    @PUT(PROFILE_URL)
    suspend fun updatePassword(@Body userEditModel: UserEditModel): Response<ResponseModel>
}