package com.example.tsunotintime.data.remote.interceptor

import android.util.Log
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.tsunotintime.data.models.RefreshTokenRequestModel
import com.example.tsunotintime.data.remote.UserService
import com.example.tsunotintime.data.storage.PrivateTokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val userService: Lazy<UserService>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = PrivateTokenStorage.getAccessToken()

        if (accessToken.isEmpty()) {
            Log.e(
                "AuthInterceptor",
                "AccessToken is empty! Request will be sent without authorization."
            )
            return chain.proceed(request)
        } else {
            val newRequest = request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

            val response = chain.proceed(newRequest)

            if (response.code == 401) {
                response.close()
                Log.e(
                    "AuthInterceptor",
                    "Need refresh"
                )
                return refreshTokenAndRetry(request, chain)

            }
            Log.e(
                "AuthInterceptor",
                "Need refresh"
            )
            return response
        }
    }


    private fun refreshTokenAndRetry(request: Request, chain: Interceptor.Chain): Response {
        return runBlocking {
            val refreshToken = PrivateTokenStorage.getRefreshToken()
            val accessToken = PrivateTokenStorage.getAccessToken()

            val decodedJWT: DecodedJWT = JWT.decode(accessToken)
            val userId = decodedJWT.getClaim("user_id").asString()

            Log.e("AuthInterceptor", "User ID: $userId")

            if (userId.isNullOrEmpty() || refreshToken.isEmpty()) {
                Log.e("AuthInterceptor", "Refresh token or userId is empty! Cannot refresh token.")
                return@runBlocking chain.proceed(request)
            }

            val refreshResponse =
                userService.value.refreshToken(RefreshTokenRequestModel(userId, refreshToken))

            if (refreshResponse.isSuccessful) {
                val newAccessToken = refreshResponse.body()?.accessToken
                val newRefreshToken = refreshResponse.body()?.refreshToken
                if (!newAccessToken.isNullOrEmpty()) {
                    Log.d("AuthInterceptor", "New access token received: $newAccessToken")

                    PrivateTokenStorage.saveAccessToken(newAccessToken)
                    newRefreshToken?.let { PrivateTokenStorage.saveRefreshToken(it) }

                    return@runBlocking chain.proceed(
                        request.newBuilder()
                            .header("Authorization", "Bearer $newAccessToken")
                            .build()
                    )
                }
            } else {
                Log.e("AuthInterceptor", "Failed to refresh token, proceeding with old request.")
            }

            return@runBlocking chain.proceed(request)
        }
    }
}