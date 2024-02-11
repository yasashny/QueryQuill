package ru.yasdev.queryquill.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.yasdev.data.LastRequest.LastRequestRepositoryImpl
import ru.yasdev.data.requestsDb.RequestDbDataSource
import ru.yasdev.data.requestsDb.RequestsDbRepositoryImpl
import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.requestsDb.RequestsDbRepository

val dataModule = module {
    singleOf(::LastRequestRepositoryImpl){ bind<LastRequestRepository>()}
    singleOf(::RequestsDbRepositoryImpl){ bind<RequestsDbRepository>()}
    singleOf(::RequestDbDataSource){ bind<RequestDbDataSource>()}
}
