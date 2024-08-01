package com.yas.queryquill.di

import com.yas.queryquill.activity.GetThemeUseCase
import com.yas.queryquill.activity.MainViewModel
import com.yas.queryquill.screens.requestScreens.viewModel.RequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::RequestViewModel)
    factoryOf(::GetThemeUseCase)
    viewModelOf(::MainViewModel)
}