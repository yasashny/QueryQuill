package org.queryquill.app.data.requests.di

import androidx.room.Room
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.queryquill.app.core.common.QQDispatchers
import org.queryquill.app.data.requests.local.TransactionsRepository
import org.queryquill.app.data.requests.local.dataSource.CurrentTransactionIdLocalDataSource
import org.queryquill.app.data.requests.local.dataSource.RequestLocalDataSource
import org.queryquill.app.data.requests.local.dataSource.ResponseLocalDataSource
import org.queryquill.app.data.requests.local.dataSource.TransactionLocalDataSource
import org.queryquill.app.data.requests.local.db.RequestsDataBase
import org.queryquill.app.data.requests.sendRequest.SendRequestLocalDataSource
import org.queryquill.app.data.requests.sendRequest.SendRequestRepository

val requestsDataModule = module {
    singleOf(::CurrentTransactionIdLocalDataSource)
    singleOf(::RequestLocalDataSource)
    singleOf(::ResponseLocalDataSource)
    singleOf(::TransactionLocalDataSource)
    singleOf(::SendRequestLocalDataSource)
    single<HttpClient> {
        HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 3000000
            }
            followRedirects = true
        }
    }
    single<TransactionsRepository> {
        TransactionsRepository(get(), get(), get(), get(), get(), get(named(QQDispatchers.IO)))
    }
    single<SendRequestRepository> {
        SendRequestRepository(get(), get(), get(), get(named(QQDispatchers.IO)))
    }
    single<RequestsDataBase> {
        Room.databaseBuilder(
            get(), RequestsDataBase::class.java, "request.db"
        ).build()
    }
}