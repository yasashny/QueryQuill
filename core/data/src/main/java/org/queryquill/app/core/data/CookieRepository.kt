package org.queryquill.app.core.data

import kotlinx.coroutines.flow.Flow
import org.queryquill.app.core.datastore.CookieDataSource

class CookieRepository internal constructor(
    private val cookieDataSource: CookieDataSource
) {

    fun getCookies(): Flow<List<String>> {
        return cookieDataSource.getCookie()
    }

    suspend fun updateCookie(list: List<String>) {
        cookieDataSource.updateCookie(list)
    }
}