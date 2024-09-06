package org.queryquill.app.feature.settings.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.settings.SettingsViewModel

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}