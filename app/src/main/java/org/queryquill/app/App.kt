/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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