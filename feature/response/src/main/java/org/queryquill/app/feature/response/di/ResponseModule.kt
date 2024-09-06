package org.queryquill.app.feature.response.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.response.ResponseViewModel

val responseModule = module {
    viewModelOf(::ResponseViewModel)
}