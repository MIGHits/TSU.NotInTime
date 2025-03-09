package com.example.tsunotintime.di

import com.example.tsunotintime.common.Constant.CONNECT_TIMEOUT
import com.example.tsunotintime.common.Constant.READ_TIMEOUT
import com.example.tsunotintime.common.Constant.WRITE_TIMEOUT
import com.example.tsunotintime.common.URL.BASE_URL
import com.example.tsunotintime.data.remote.AuthService
import com.example.tsunotintime.data.remote.interceptor.AuthInterceptor
import com.example.tsunotintime.domain.repository.AuthRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        AuthInterceptor(get())
    }

    single {
        OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    single { get<Retrofit>().create(AuthService::class.java) }
}