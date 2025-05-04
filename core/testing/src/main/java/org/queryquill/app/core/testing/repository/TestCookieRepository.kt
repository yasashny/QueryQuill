package org.queryquill.app.core.testing.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.queryquill.app.core.data.CookieRepository

class TestCookieRepository : CookieRepository {

    private val cookieFlow = MutableSharedFlow<List<String>>(replay = 1)

    override fun getCookies(): Flow<List<String>> {
        return cookieFlow.asSharedFlow()
    }

    override suspend fun updateCookie(list: List<String>) {
        cookieFlow.tryEmit(list)
    }
}