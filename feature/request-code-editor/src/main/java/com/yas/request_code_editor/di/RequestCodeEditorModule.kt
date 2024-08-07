package com.yas.request_code_editor.di

import com.yas.request_code_editor.RequestCodeEditorViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val requestCodeEditorModule = module {
    viewModelOf(::RequestCodeEditorViewModel)
}