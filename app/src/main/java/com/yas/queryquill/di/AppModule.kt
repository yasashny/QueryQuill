package com.yas.queryquill.di

import com.yas.queryquill.screens.requestScreens.viewModel.RequestViewModel
import com.yas.queryquill.screens.settingsScreen.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::RequestViewModel)
    viewModelOf(::SettingsViewModel)
}