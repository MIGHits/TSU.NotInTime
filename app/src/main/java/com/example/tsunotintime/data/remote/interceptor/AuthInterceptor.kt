package com.example.tsunotintime.data.remote.interceptor

import com.brendangoldberg.kotlin_jwt.KtJwtDecoder
import com.example.tsunotintime.data.models.RefreshTokenRequestModel
import com.example.tsunotintime.data.models.UserIdClaim
import com.example.tsunotintime.data.remote.AuthService
import com.example.tsunotintime.data.storage.PrivateTokenStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val authService: AuthService) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val accessToken = PrivateTokenStorage.getAccessToken()

        if (accessToken.isEmpty()) {
            return chain.proceed(request)
        }

        val newRequest = request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()


        val response = chain.proceed(newRequest)

        if (response.code == 401) {
            response.close()
            return refreshTokenAndRetry(request, chain)
        }

        return response
    }

    private fun refreshTokenAndRetry(request: Request, chain: Interceptor.Chain): Response {
        return runBlocking {
            val refreshToken = PrivateTokenStorage.getRefreshToken()
            val userId = KtJwtDecoder.decode(PrivateTokenStorage.getAccessToken())
                .getClaim("userId", UserIdClaim.serializer())?.userId.toString()

            val refreshResponse =
                authService.refreshToken(RefreshTokenRequestModel(userId, refreshToken))

            if (refreshResponse.isSuccessful) {
                val newAccessToken = refreshResponse.body()?.accessToken.toString()
                PrivateTokenStorage.saveAccessToken(newAccessToken)


                val newRequest = request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()


                chain.proceed(newRequest)
            } else {
                chain.proceed(request)
            }
        }
    }
}