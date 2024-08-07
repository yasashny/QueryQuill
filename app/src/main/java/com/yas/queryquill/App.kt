package com.yas.queryquill

import android.app.Application
import com.yas.new_request.di.newRequestModule
import com.yas.queryquill.di.appModule
import com.yas.request_code_editor.di.requestCodeEditorModule
import com.yas.requests.di.requestsDataModule
import com.yas.response.di.responseModule
import com.yas.settings.di.settingsDataModule
import com.yas.settings.di.settingsModule
import com.yas.transaction.di.transactionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    requestsDataModule,
                    settingsDataModule,
                    settingsModule,
                    responseModule,
                    newRequestModule,
                    transactionModule,
                    requestCodeEditorModule
                )
            )
        }
    }
}