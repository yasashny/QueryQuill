package com.yas.queryquill

import android.app.Application
import com.yas.common.commonModule
import com.yas.domain.di.domainModule
import com.yas.new_transaction.di.newTransactionModule
import com.yas.queryquill.di.appModule
import com.yas.request.di.requestModule
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
                    newTransactionModule,
                    transactionModule,
                    requestCodeEditorModule,
                    domainModule,
                    commonModule,
                    requestModule
                )
            )
        }
    }
}