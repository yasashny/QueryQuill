package org.queryquill.app.core.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.queryquill.app.core.domain.GetThemeUseCase

val domainModule = module {
    factoryOf(::GetThemeUseCase)
}