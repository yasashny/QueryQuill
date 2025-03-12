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