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

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.feature.cookie.util.TestTags

class CookieScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showsLoadingIndicator() {
        val uiState: CookieUiState = CookieUiState.Loading
        composeTestRule.setContent {
            CookieScreen(uiState = uiState, onEvent = {}, navigateUp = {}, saveCookieOnStop = {})
        }
        composeTestRule.onNodeWithTag(TestTags.CookieScreen.LOADING_INDICATOR).assertExists()
    }

    @Test
    fun whenSuccessState_showsCookieList() {
        val cookies = listOf(
            CookieModel(1, "cookie1"), CookieModel(2, "cookie2")
        )
        val uiState = CookieUiState.Success(cookies)
        composeTestRule.setContent {
            CookieScreen(uiState = uiState, onEvent = {}, navigateUp = {}, saveCookieOnStop = {})
        }
        composeTestRule.onNodeWithText("cookie1").assertExists()
        composeTestRule.onNodeWithText("cookie2").assertExists()
    }

    @Test
    fun whenAddButtonClicked_triggersAddEvent() {
        var capturedEvent: UpdateCookie? = null
        val uiState = CookieUiState.Success(emptyList())
        composeTestRule.setContent {
            CookieScreen(
                uiState = uiState,
                onEvent = { event -> capturedEvent = event },
                navigateUp = {},
                saveCookieOnStop = {})
        }
        composeTestRule.onNodeWithTag(TestTags.CookieScreen.ADD_COOKIE_BUTTON).performClick()
        assert(capturedEvent == UpdateCookie.Add)
    }

    @Test
    fun whenNavigateUpCalled_navigateUpIsInvoked() {
        var navigateUpCalled = false
        val uiState = CookieUiState.Success(emptyList())
        composeTestRule.setContent {
            CookieScreen(
                uiState = uiState,
                onEvent = {},
                navigateUp = { navigateUpCalled = true },
                saveCookieOnStop = {})
        }
        composeTestRule.onNodeWithTag(
            TestTags.CookieScreen.NAVIGATE_UP_BUTTON
        ).performClick()
        assert(navigateUpCalled) { "Navigate up should be called when back button is clicked" }
    }

    @Test
    fun whenScreenStopped_saveCookieOnStopIsInvoked() = runTest {
        var saveCalled = false
        val uiState = CookieUiState.Success(emptyList())
        val testLifecycleOwner = TestLifecycleOwner()
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLifecycleOwner provides testLifecycleOwner
            ) {
                CookieScreen(
                    uiState = uiState,
                    onEvent = {},
                    navigateUp = {},
                    saveCookieOnStop = { saveCalled = true })
            }
        }
        testLifecycleOwner.setCurrentState(Lifecycle.State.STARTED)
        testLifecycleOwner.setCurrentState(Lifecycle.State.CREATED)
        composeTestRule.waitForIdle()
        assert(saveCalled) { "SaveCookieOnStop should be called when lifecycle moves to CREATED state" }
    }
}
