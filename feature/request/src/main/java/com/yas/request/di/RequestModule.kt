package com.yas.request.di

import com.yas.request.RequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val requestModule = module {
    viewModelOf(::RequestViewModel)
}