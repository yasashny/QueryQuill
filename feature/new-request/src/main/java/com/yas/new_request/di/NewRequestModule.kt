package com.yas.new_request.di

import com.yas.new_request.NewRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val newRequestModule = module {
    viewModelOf(::NewRequestViewModel)
}