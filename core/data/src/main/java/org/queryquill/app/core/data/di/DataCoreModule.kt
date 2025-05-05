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