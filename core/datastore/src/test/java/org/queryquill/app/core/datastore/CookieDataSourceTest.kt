package org.queryquill.app.core.datastore

import androidx.datastore.preferences.core.mutablePreferencesOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CookieDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: CookieDataSource

    @Before
    fun setup() {
        subject = CookieDataSource(InMemoryDataStore(mutablePreferencesOf()))
    }

    @Test
    fun `getCookie initially returns empty list`() = testScope.runTest {
        val result = subject.getCookie().first()
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun `updateCookie stores and retrieves new list`() = testScope.runTest {
        val testCookies = listOf("session=123", "user=john")
        subject.updateCookie(testCookies)
        val result = subject.getCookie().first()
        assertEquals(testCookies, result)
    }

    @Test
    fun `multiple updates retain last value`() = testScope.runTest {
        val firstList = listOf("a")
        val secondList = listOf("b", "c")
        subject.updateCookie(firstList)
        subject.updateCookie(secondList)
        val result = subject.getCookie().first()
        assertEquals(secondList, result)
    }

    @Test
    fun `update with empty list clears data`() = testScope.runTest {
        subject.updateCookie(listOf("temp"))
        subject.updateCookie(emptyList())
        val result = subject.getCookie().first()
        assertEquals(emptyList<String>(), result)
    }
}