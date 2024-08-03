package com.yas.requests.di

import com.yas.requests.local.RequestsRepository
import com.yas.requests.local.storage.CurrentRequestStorage
import com.yas.requests.local.storage.RequestsStorage
import com.yas.requests.sendRequest.SendRequestDataSource
import com.yas.requests.sendRequest.SendRequestRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val requestsDataModule = module {
    singleOf(::CurrentRequestStorage)
    singleOf(::RequestsRepository)
    singleOf(::RequestsStorage)
    singleOf(::SendRequestRepository)
    singleOf(::SendRequestDataSource)
    single<HttpClient> {
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
            }
            followRedirects = true
        }
    }
}