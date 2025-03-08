package com.example.tsunotintime

import android.app.Application
import com.example.tsunotintime.di.appModule
import com.example.tsunotintime.di.dataModule
import com.example.tsunotintime.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppContext : Application() {
    companion object {
        lateinit var instance: AppContext
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@AppContext)
            modules(appModule, dataModule, domainModule)
        }
    }
}
