package com.yas.domain.di

import com.yas.domain.GetThemeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetThemeUseCase)
}