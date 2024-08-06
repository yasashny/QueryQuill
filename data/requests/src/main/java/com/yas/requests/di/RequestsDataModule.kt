package com.yas.requests.di

import com.yas.requests.local.RequestsRepository
import com.yas.requests.local.CurrentRequestLocalDataSource
import com.yas.requests.local.RequestsLocalDataSource
import com.yas.requests.sendRequest.SendRequestLocalDataSource
import com.yas.requests.sendRequest.SendRequestRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val requestsDataModule = module {
    singleOf(::CurrentRequestLocalDataSource)
    singleOf(::RequestsRepository)
    singleOf(::RequestsLocalDataSource)
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