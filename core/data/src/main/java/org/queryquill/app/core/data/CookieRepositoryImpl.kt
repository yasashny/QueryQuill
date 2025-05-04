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