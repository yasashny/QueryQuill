package com.yas.queryquill.di

import com.yas.domain.GetThemeUseCase
import com.yas.queryquill.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
    factoryOf(::GetThemeUseCase)
    viewModelOf(::MainViewModel)
}