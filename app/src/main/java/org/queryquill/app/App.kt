package org.queryquill.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.queryquill.app.core.common.commonModule
import org.queryquill.app.core.data.di.dataCoreModule
import org.queryquill.app.core.database.di.databaseCoreModule
import org.queryquill.app.core.datastore.di.dataStoreCoreModule
import org.queryquill.app.core.domain.di.domainModule
import org.queryquill.app.core.network.di.networkCoreModule
import org.queryquill.app.di.appModule
import org.queryquill.app.feature.cookie.di.cookieModule
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
                    settingsModule,
                    responseModule,
                    newTransactionModule,
                    transactionModule,
                    requestCodeEditorModule,
                    domainModule,
                    commonModule,
                    requestModule,
                    cookieModule,
                    databaseCoreModule,
                    dataStoreCoreModule,
                    networkCoreModule,
                    dataCoreModule
                )
            )
        }
    }
}