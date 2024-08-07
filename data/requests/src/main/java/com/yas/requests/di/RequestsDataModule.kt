package com.yas.requests.di

import com.yas.requests.local.TransactionsRepository
import com.yas.requests.local.dataSource.CurrentTransactionIdLocalDataSource
import com.yas.requests.local.dataSource.RequestLocalDataSource
import com.yas.requests.local.dataSource.ResponseLocalDataSource
import com.yas.requests.local.dataSource.TransactionLocalDataSource
import com.yas.requests.sendRequest.SendRequestLocalDataSource
import com.yas.requests.sendRequest.SendRequestRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val requestsDataModule = module {
    singleOf(::CurrentTransactionIdLocalDataSource)
    singleOf(::TransactionsRepository)
    singleOf(::RequestLocalDataSource)
    singleOf(::ResponseLocalDataSource)
    singleOf(::TransactionLocalDataSource)
    singleOf(::SendRequestRepository)
    singleOf(::SendRequestLocalDataSource)
    single<HttpClient> {
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
            }
            followRedirects = true
        }
    }
}