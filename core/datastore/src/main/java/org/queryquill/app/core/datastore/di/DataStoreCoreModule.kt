package org.queryquill.app.core.datastore.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.queryquill.app.core.datastore.CurrentTransactionIdDataSource
import org.queryquill.app.core.datastore.SettingsDataSource

val dataStoreCoreModule = module {
    singleOf(::SettingsDataSource)
    singleOf(::CurrentTransactionIdDataSource)
}