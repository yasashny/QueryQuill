package org.queryquill.app.feature.transaction.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.queryquill.app.feature.transaction.TransactionViewModel

val transactionModule = module {
    viewModelOf(::TransactionViewModel)
}