package org.queryquill.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.queryquill.app.core.common.commonModule
import org.queryquill.app.core.domain.di.domainModule
import org.queryquill.app.data.requests.di.requestsDataModule
import org.queryquill.app.data.settings.di.settingsDataModule
import org.queryquill.app.di.appModule
import org.queryquill.app.feature.new_transaction.di.newTransactionModule
import org.queryquill.app.feature.request.di.requestModule
import org.queryquill.app.feature.request_code_editor.di.requestCodeEditorModule
import org.queryquill.app.feature.response.di.responseModule
import org.queryquill.app.feature.settings.di.settingsModule
import org.queryquill.app.feature.transaction.di.transactionModule

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