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

package org.queryquill.app.feature.cookie

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.testing.repository.TestCookieRepository
import org.queryquill.app.core.testing.util.MainDispatcherRule

class CookieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val cookieRepository = TestCookieRepository()

    private lateinit var viewModel: CookieViewModel

    @Before
    fun setup() {
        viewModel = CookieViewModel(cookieRepository)
    }

    @Test
    fun `Initial state is Loading`() = runTest {
        assertEquals(CookieUiState.Loading, viewModel.cookieState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `State is success after data loaded`() = runTest {
        val testCookies = listOf("test_cookie=value")
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.cookieState.collect() }

        cookieRepository.updateCookie(testCookies)

        val currentState = viewModel.cookieState.value
        assert(currentState is CookieUiState.Success)
        currentState as CookieUiState.Success
        assertEquals(testCookies[0], currentState.list[0].cookie)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Add new cookie test`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.cookieState.collect() }

        cookieRepository.updateCookie(emptyList())
        viewModel.onEvent(UpdateCookie.Add)

        val currentState = viewModel.cookieState.value as CookieUiState.Success
        assertEquals(1, currentState.list.size)
        assertEquals(
            "a=b; Expires=Mon, 1 Jan 2345 11:11:23 GMT; Domain=example.com; Path=/",
            currentState.list[0].cookie
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Delete cookie test`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.cookieState.collect() }

        cookieRepository.updateCookie(listOf("cookie1", "cookie2"))
        viewModel.onEvent(UpdateCookie.Delete(0))

        val currentState = viewModel.cookieState.value as CookieUiState.Success
        assertEquals(1, currentState.list.size)
        assertEquals("cookie2", currentState.list[0].cookie)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Update cookie test`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.cookieState.collect() }

        cookieRepository.updateCookie(listOf("old_cookie"))
        val newCookie = CookieModel(0, "new_cookie")
        viewModel.onEvent(UpdateCookie.Update(0, newCookie))

        val currentState = viewModel.cookieState.value as CookieUiState.Success
        assertEquals(1, currentState.list.size)
        assertEquals("new_cookie", currentState.list[0].cookie)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Save cookies test`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.cookieState.collect() }

        val initialCookies = listOf("cookie1", "cookie2")
        cookieRepository.updateCookie(initialCookies)
        viewModel.saveCookie()

        val currentState = viewModel.cookieState.value as CookieUiState.Success
        assertEquals(initialCookies, currentState.list.map { it.cookie })
    }
}