package ru.yasdev.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.yasdev.data.lastRequest.LastRequestDataSource
import ru.yasdev.data.lastRequest.LastRequestRepositoryImpl
import ru.yasdev.data.requestsDb.RequestDbDataSource
import ru.yasdev.data.requestsDb.RequestsDbRepositoryImpl
import ru.yasdev.data.sendRequest.SendRequestDataSource
import ru.yasdev.data.sendRequest.SendRequestRepositoryImpl
import ru.yasdev.domain.lastRequest.repositories.LastRequestRepository
import ru.yasdev.domain.requestsDb.repositories.RequestsDbRepository
import ru.yasdev.domain.sendRequest.SendRequestRepository

val dataModule = module {
    singleOf(::LastRequestRepositoryImpl) { bind<LastRequestRepository>() }
    singleOf(::LastRequestDataSource)
    singleOf(::RequestsDbRepositoryImpl) { bind<RequestsDbRepository>() }
    singleOf(::RequestDbDataSource)
    singleOf(::SendRequestRepositoryImpl) { bind<SendRequestRepository>() }
    singleOf(::SendRequestDataSource)
    single<HttpClient> { HttpClient(CIO) }
}