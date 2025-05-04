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

class CookieScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showsLoadingIndicator() {
        val uiState: CookieUiState = CookieUiState.Loading
        composeTestRule.setContent {
            CookieScreen(uiState = uiState, onEvent = {}, navigateUp = {}, saveCookieOnStop = {})
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
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
        composeTestRule.onNodeWithTag("add_cookie_button").performClick()
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
            "navigate_up_button"
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
