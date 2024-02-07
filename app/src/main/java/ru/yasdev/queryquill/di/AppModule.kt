package ru.yasdev.queryquill.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yasdev.queryquill.screens.httpRequestScreen.HttpRequestScreenViewModel
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreenViewModel

val appModule = module {
    viewModel<HttpResponseScreenViewModel> { HttpResponseScreenViewModel() }
    viewModel<HttpRequestScreenViewModel> { HttpRequestScreenViewModel() }
}