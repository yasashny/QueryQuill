package ru.yasdev.queryquill

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

import ru.yasdev.queryquill.di.appModule
import ru.yasdev.queryquill.di.dataModule
import ru.yasdev.queryquill.di.domainModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}