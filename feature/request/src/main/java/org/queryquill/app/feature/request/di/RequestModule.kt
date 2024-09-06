package org.queryquill.app.feature.request.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.request.RequestViewModel

val requestModule = module {
    viewModelOf(::RequestViewModel)
}