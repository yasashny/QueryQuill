package com.yas.settings.di

import com.yas.settings.SettingsLocalDataSource
import com.yas.settings.SettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDataModule = module {
    singleOf(::SettingsRepository)
    singleOf(::SettingsLocalDataSource)
}