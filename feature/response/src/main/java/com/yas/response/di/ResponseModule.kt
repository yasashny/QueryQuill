package com.yas.response.di

import com.yas.response.ResponseViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val responseModule = module {
    viewModelOf(::ResponseViewModel)
}