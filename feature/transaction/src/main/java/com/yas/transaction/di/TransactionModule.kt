package com.yas.transaction.di

import com.yas.transaction.TransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val transactionModule = module {
    viewModelOf(::TransactionViewModel)
}