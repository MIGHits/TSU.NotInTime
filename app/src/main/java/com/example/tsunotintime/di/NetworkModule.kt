package com.example.tsunotintime.di

import com.example.tsunotintime.common.MyClient
import com.example.tsunotintime.common.URL.BASE_URL
import com.example.tsunotintime.data.remote.RequestService
import com.example.tsunotintime.data.remote.UserService
import com.example.tsunotintime.data.remote.interceptor.AuthInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { lazy { get<Retrofit>().create(UserService::class.java) } }
    single { AuthInterceptor(get()) }
    single {
        MyClient.getUnsafeOkHttpClient(get())
    }

    single {
        MyClient.getImageLoader(get())
    }

    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(RequestService::class.java) }
}