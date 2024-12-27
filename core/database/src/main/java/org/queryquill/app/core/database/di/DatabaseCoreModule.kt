package org.queryquill.app.core.database.di

import androidx.room.Room
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.queryquill.app.core.database.QQDatabase
import org.queryquill.app.core.database.RequestDataSource
import org.queryquill.app.core.database.ResponseDataSource
import org.queryquill.app.core.database.TransactionDataSource
import org.queryquill.app.core.database.dao.RequestDao
import org.queryquill.app.core.database.dao.ResponseDao
import org.queryquill.app.core.database.dao.TransactionDao

val databaseCoreModule = module {
    single<QQDatabase> {
        Room.databaseBuilder(
            get(), QQDatabase::class.java, "request.db"
        ).build()
    }
    single<RequestDao> {
        get<QQDatabase>().requestDao
    }
    single<ResponseDao> {
        get<QQDatabase>().responseDao
    }
    single<TransactionDao> {
        get<QQDatabase>().transactionDao
    }
    singleOf(::RequestDataSource)
    singleOf(::ResponseDataSource)
    singleOf(::TransactionDataSource)
}