/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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