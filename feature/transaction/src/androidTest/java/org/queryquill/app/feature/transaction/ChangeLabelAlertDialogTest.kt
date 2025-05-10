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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.feature.transaction.util.TestTags

class ChangeLabelAlertDialogTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun changeLabelAlertDialog_rendersAllComponents() {
        composeRule.setContent {
            ChangeLabelAlertDialog(onDismiss = {}, onConfirm = {})
        }
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.LABEL_INPUT).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.LABEL_TEXT).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.CONFIRM_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.CANCEL_BUTTON).assertIsDisplayed()
    }

    @Test
    fun changeLabelAlertDialog_cancelButtonDismissesDialog() {
        var dismissed = false
        composeRule.setContent {
            ChangeLabelAlertDialog(onDismiss = { dismissed = true }, onConfirm = {})
        }
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.CANCEL_BUTTON).performClick()
        assert(dismissed)
    }

    @Test
    fun changeLabelAlertDialog_confirmButtonCallsOnConfirmWithInput() {
        var confirmedLabel = ""
        val testInput = "Test Label"
        composeRule.setContent {
            ChangeLabelAlertDialog(onDismiss = {}, onConfirm = { confirmedLabel = it })
        }
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.LABEL_TEXT).performTextInput(testInput)
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.CONFIRM_BUTTON).performClick()
        assert(confirmedLabel == testInput)
    }

    @Test
    fun changeLabelAlertDialog_confirmButtonDisabledWhenInputEmpty() {
        composeRule.setContent {
            ChangeLabelAlertDialog(onDismiss = {}, onConfirm = {})
        }
        composeRule.onNodeWithTag(TestTags.ChangeLabelDialog.CONFIRM_BUTTON)
            .assertIsNotEnabled()
    }
}