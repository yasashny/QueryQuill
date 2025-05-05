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