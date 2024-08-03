package com.yas.queryquill.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

import com.yas.queryquill.di.appModule
import com.yas.requests.di.requestsDataModule
import com.yas.settings.di.settingsModule
import com.yas.settings.di.settingsDataModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule,
                requestsDataModule, settingsDataModule, settingsModule))
        }
    }
}