package com.yas.settings.di

import com.yas.common.QQDispatchers
import com.yas.settings.SettingsLocalDataSource
import com.yas.settings.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsDataModule = module {
    singleOf(::SettingsLocalDataSource)
    single<SettingsRepository> {
        SettingsRepository(get(), get(named(QQDispatchers.IO)))
    }
}