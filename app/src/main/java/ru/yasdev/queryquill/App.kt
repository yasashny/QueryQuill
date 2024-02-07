package ru.yasdev.queryquill

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin

import ru.yasdev.queryquill.di.appModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule))
        }
    }
}