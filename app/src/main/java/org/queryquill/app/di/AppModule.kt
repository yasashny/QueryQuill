package org.queryquill.app.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.queryquill.app.MainViewModel
import org.queryquill.app.core.domain.GetThemeUseCase

val appModule = module {
    factoryOf(::GetThemeUseCase)
    viewModelOf(::MainViewModel)
}