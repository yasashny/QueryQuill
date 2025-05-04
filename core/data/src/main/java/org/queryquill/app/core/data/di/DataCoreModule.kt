package org.queryquill.app.core.data.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.queryquill.app.core.common.QQDispatchers
import org.queryquill.app.core.data.CookieRepository
import org.queryquill.app.core.data.CookieRepositoryImpl
import org.queryquill.app.core.data.SendRequestRepository
import org.queryquill.app.core.data.SettingsRepository
import org.queryquill.app.core.data.SettingsRepositoryImpl
import org.queryquill.app.core.data.TransactionRepository
import org.queryquill.app.core.data.TransactionRepositoryImpl

val dataCoreModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get(named(QQDispatchers.IO)))
    }
    single<TransactionRepository> {
        TransactionRepositoryImpl(
            get(), get(), get(), get(), get(named(QQDispatchers.IO))
        )
    }
    single<SendRequestRepository> {
        SendRequestRepository(
            get(), get(), get(), get(named(QQDispatchers.IO))
        )
    }
    single<CookieRepository> {
        CookieRepositoryImpl(get(), get(named(QQDispatchers.IO)))
    }
}