package org.queryquill.app.data.settings.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.queryquill.app.core.common.QQDispatchers
import org.queryquill.app.data.settings.SettingsLocalDataSource
import org.queryquill.app.data.settings.SettingsRepository

val settingsDataModule = module {
    singleOf(::SettingsLocalDataSource)
    single<SettingsRepository> {
        SettingsRepository(get(), get(named(QQDispatchers.IO)))
    }
}