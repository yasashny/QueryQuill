package com.yas.queryquill.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.yas.queryquill.screens.requestScreens.viewModel.RequestViewModel

val appModule = module {
    viewModelOf(::RequestViewModel)
}