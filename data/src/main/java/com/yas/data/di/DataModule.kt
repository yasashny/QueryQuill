package com.yas.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.yas.data.lastRequest.LastRequestDataSource
import com.yas.data.lastRequest.LastRequestRepositoryImpl
import com.yas.data.local.RequestDbStorage
import com.yas.data.local.LocalDbRepositoryImpl
import com.yas.data.sendRequest.SendRequestDataSource
import com.yas.data.sendRequest.SendRequestRepositoryImpl
import com.yas.domain.lastRequest.repositories.LastRequestRepository
import com.yas.domain.requestsDb.repositories.LocalDbRepository
import com.yas.domain.sendRequest.SendRequestRepository

val dataModule = module {
    singleOf(::LastRequestRepositoryImpl) { bind<LastRequestRepository>() }
    singleOf(::LastRequestDataSource)
    singleOf(::LocalDbRepositoryImpl) { bind<LocalDbRepository>() }
    singleOf(::RequestDbStorage)
    singleOf(::SendRequestRepositoryImpl) { bind<SendRequestRepository>() }
    singleOf(::SendRequestDataSource)
        single<HttpClient> { HttpClient(CIO){
            install(HttpTimeout){
                requestTimeoutMillis = 30000
            }
            followRedirects = true
    } }
}