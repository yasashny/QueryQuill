package com.yas.settings.di

import com.yas.settings.SettingsViewModel
import com.yas.settings.useCase.GetSettingsUseCase
import com.yas.settings.useCase.UpdateSettingsUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsModule = module {
    factoryOf(::GetSettingsUseCase)
    factoryOf(::UpdateSettingsUseCase)
    viewModelOf(::SettingsViewModel)
}