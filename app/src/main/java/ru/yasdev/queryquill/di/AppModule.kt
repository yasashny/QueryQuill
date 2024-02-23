package ru.yasdev.queryquill.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.screens.requestScreens.NewRequestScreenViewModel
import ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreenViewModel

val appModule = module {
    viewModelOf(::MainActivityViewModel)
    viewModelOf(::HttpResponseScreenViewModel)
    viewModelOf(::NewRequestScreenViewModel)
}