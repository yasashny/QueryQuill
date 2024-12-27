package org.queryquill.app.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.queryquill.app.core.network.SendRequestDataSource

val networkCoreModule = module {
    single<HttpClient> {
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 3000000
            }
            followRedirects = true
        }
    }
    singleOf(::SendRequestDataSource)
}