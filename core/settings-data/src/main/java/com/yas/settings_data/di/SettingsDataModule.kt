package com.yas.settings_data.di

import com.yas.settings_data.SettingsRepository
import com.yas.settings_data.SettingsStorage
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDataModule = module {
    singleOf(::SettingsRepository)
    singleOf(::SettingsStorage)
}