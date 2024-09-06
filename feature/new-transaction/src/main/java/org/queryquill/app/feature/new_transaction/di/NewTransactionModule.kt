package org.queryquill.app.feature.new_transaction.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.new_transaction.NewTransactionViewModel

val newTransactionModule = module {
    viewModelOf(::NewTransactionViewModel)
}