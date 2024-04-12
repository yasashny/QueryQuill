package ru.yasdev.queryquill.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.yasdev.data.di.dataModule
import ru.yasdev.domain.di.domainModule

import ru.yasdev.queryquill.di.appModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}