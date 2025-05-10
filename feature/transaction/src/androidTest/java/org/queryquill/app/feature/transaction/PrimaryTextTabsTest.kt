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

package org.queryquill.app.feature.transaction

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PrimaryTextTabsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun primaryTextTabs_displaysRequestAndResponseTabs() {
        val tabsScreenState = mutableStateOf(TabsScreenState.REQUEST)
        composeTestRule.setContent {
            PrimaryTextTabs(tabsScreenState)
        }
        composeTestRule.onNodeWithTag("Request").assertExists()
        composeTestRule.onNodeWithTag("Response").assertExists()
    }

    @Test
    fun primaryTextTabs_initialStateIsRequestTabSelected() {
        val tabsScreenState = mutableStateOf(TabsScreenState.REQUEST)
        composeTestRule.setContent {
            PrimaryTextTabs(tabsScreenState)
        }
        assertEquals(TabsScreenState.REQUEST, tabsScreenState.value)
    }

    @Test
    fun primaryTextTabs_clickingResponseTabChangesState() {
        val tabsScreenState = mutableStateOf(TabsScreenState.REQUEST)
        composeTestRule.setContent {
            PrimaryTextTabs(tabsScreenState)
        }
        composeTestRule.onNodeWithTag("Response").performClick()
        assertEquals(TabsScreenState.RESPONSE, tabsScreenState.value)
    }

    @Test
    fun primaryTextTabs_clickingRequestTabChangesStateBack() {
        val tabsScreenState = mutableStateOf(TabsScreenState.RESPONSE)
        composeTestRule.setContent {
            PrimaryTextTabs(tabsScreenState)
        }
        composeTestRule.onNodeWithTag("Request").performClick()
        assertEquals(TabsScreenState.REQUEST, tabsScreenState.value)
    }
}