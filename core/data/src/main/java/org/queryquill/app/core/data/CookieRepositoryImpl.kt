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

package org.queryquill.app.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.queryquill.app.core.datastore.CookieDataSource

class CookieRepositoryImpl internal constructor(
    private val cookieDataSource: CookieDataSource, private val ioDispatcher: CoroutineDispatcher
) : CookieRepository {

    override fun getCookies(): Flow<List<String>> {
        return cookieDataSource.getCookie().flowOn(ioDispatcher)
    }

    override suspend fun updateCookie(list: List<String>) {
        withContext(ioDispatcher) {
            cookieDataSource.updateCookie(list)
        }
    }
}