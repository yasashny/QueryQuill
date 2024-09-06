package org.queryquill.app.feature.request_code_editor.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.request_code_editor.RequestCodeEditorViewModel

val requestCodeEditorModule = module {
    viewModelOf(::RequestCodeEditorViewModel)
}