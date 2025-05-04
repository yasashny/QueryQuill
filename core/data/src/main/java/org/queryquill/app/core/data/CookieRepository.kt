package org.queryquill.app.core.data

import kotlinx.coroutines.flow.Flow

interface CookieRepository {
    fun getCookies(): Flow<List<String>>
    suspend fun updateCookie(list: List<String>)
}