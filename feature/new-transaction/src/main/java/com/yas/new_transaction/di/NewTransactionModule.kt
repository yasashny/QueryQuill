package com.yas.new_transaction.di

import com.yas.new_transaction.NewTransactionViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val newTransactionModule = module {
    viewModelOf(::NewTransactionViewModel)
}