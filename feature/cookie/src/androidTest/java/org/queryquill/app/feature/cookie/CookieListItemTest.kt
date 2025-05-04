package org.queryquill.app.feature.cookie

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Rule
import org.junit.Test

class CookieListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cookieListItem_displaysInitialValue() {
        val testCookie = CookieModel(1, "Test Cookie")
        composeTestRule.setContent {
            CookieListItem(
                item = testCookie, index = 0, onEvent = {})
        }
        composeTestRule.onNodeWithTag("cookie_input").assertIsDisplayed()
    }

    @Test
    fun cookieListItem_triggersUpdateEventOnTextChange() {
        var lastEvent: UpdateCookie? = null
        val testCookie = CookieModel(1, "Initial Cookie")
        composeTestRule.setContent {
            CookieListItem(
                item = testCookie, index = 0, onEvent = { event -> lastEvent = event })
        }
        composeTestRule.onNodeWithTag("cookie_input").performTextReplacement("New Value")
        assert(lastEvent is UpdateCookie.Update)
        val updateEvent = lastEvent as UpdateCookie.Update
        assert(updateEvent.id == 0)
        assert(updateEvent.newCookieState.id == testCookie.id)
        assert(updateEvent.newCookieState.cookie == "New Value")
    }


    @Test
    fun cookieListItem_triggersDeleteEventOnButtonClick() {
        var lastEvent: UpdateCookie? = null
        val testCookie = CookieModel(1, "Test Cookie")
        composeTestRule.setContent {
            CookieListItem(
                item = testCookie, index = 0, onEvent = { event -> lastEvent = event })
        }
        composeTestRule.onNodeWithTag("delete_cookie").performClick()
        assert(lastEvent is UpdateCookie.Delete)
        val deleteEvent = lastEvent as UpdateCookie.Delete
        assert(deleteEvent.id == 0)
    }
}

