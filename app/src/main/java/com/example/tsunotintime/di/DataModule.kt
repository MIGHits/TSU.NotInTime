package com.example.tsunotintime.di

import com.example.tsunotintime.data.repository.ValidationRepositoryImpl
import com.example.tsunotintime.domain.repository.ValidationRepository
import org.koin.dsl.module

val dataModule = module {
    factory<ValidationRepository>{
        ValidationRepositoryImpl()
    }
}