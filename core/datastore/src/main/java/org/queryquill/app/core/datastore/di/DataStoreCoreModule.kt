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

package org.queryquill.app.core.datastore.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.queryquill.app.core.datastore.CookieDataSource
import org.queryquill.app.core.datastore.CurrentTransactionIdDataSource
import org.queryquill.app.core.datastore.SettingsDataSource

val dataStoreCoreModule = module {
    single(named(DataStoreName.COOKIE)) {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile(DataStoreName.COOKIE) })
    }
    single(named(DataStoreName.SETTINGS)) {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile(DataStoreName.SETTINGS) })
    }
    single(named(DataStoreName.CURRENT_TRANSACTION)) {
        PreferenceDataStoreFactory.create(
            produceFile = { get<Context>().preferencesDataStoreFile(DataStoreName.CURRENT_TRANSACTION) })
    }
    single<SettingsDataSource> {
        SettingsDataSource(get(named(DataStoreName.SETTINGS)))
    }
    single<CurrentTransactionIdDataSource> {
        CurrentTransactionIdDataSource(get(named(DataStoreName.CURRENT_TRANSACTION)))
    }
    single<CookieDataSource> {
        CookieDataSource(get(named(DataStoreName.COOKIE)))
    }
}

private object DataStoreName {
    const val COOKIE = "cookie"
    const val SETTINGS = "settings"
    const val CURRENT_TRANSACTION = "current_transaction"
}