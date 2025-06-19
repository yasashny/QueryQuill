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

package org.queryquill.app.feature.request.dialog

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.feature.request.utils.TestTags

class ChangeTypeDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysDialogWithCorrectTitleAndMessage() {
        composeTestRule.setContent {
            ChangeTypeDialog(title = "body", onDismiss = {}, onConfirm = {})
        }
        composeTestRule.onNodeWithText("Switch body type?").assertExists()
        composeTestRule.onNodeWithText("Current body will be lost. Are you sure you want to continue?")
            .assertExists()
    }

    @Test
    fun confirmButtonCallsOnConfirm() {
        var confirmed = false
        composeTestRule.setContent {
            ChangeTypeDialog(title = "body", onDismiss = {}, onConfirm = { confirmed = true })
        }
        composeTestRule.onNodeWithTag(TestTags.ChangeTypeDialog.CONFIRM_BUTTON).performClick()
        assert(confirmed)
    }

    @Test
    fun dismissButtonCallsOnDismiss() {
        var dismissed = false
        composeTestRule.setContent {
            ChangeTypeDialog(title = "body", onDismiss = { dismissed = true }, onConfirm = {})
        }
        composeTestRule.onNodeWithTag(TestTags.ChangeTypeDialog.DISMISS_BUTTON).performClick()
        assert(dismissed)
    }
}