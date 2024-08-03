package com.yas.settings.di

import com.yas.settings.SettingsRepository
import com.yas.settings.SettingsStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDataModule = module {
    singleOf(::SettingsRepository)
    singleOf(::SettingsStorage)
}