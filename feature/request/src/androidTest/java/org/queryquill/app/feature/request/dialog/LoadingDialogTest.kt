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
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.feature.request.utils.TestTags

class LoadingDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysLoadingIndicatorAndCancelButton() {
        composeTestRule.setContent {
            LoadingDialog(onDismiss = {})
        }
        composeTestRule.onNodeWithTag(TestTags.LoadingDialog.LOADING_INDICATOR).assertExists()
        composeTestRule.onNodeWithTag(TestTags.LoadingDialog.CANCEL_BUTTON).assertExists()
    }

    @Test
    fun callsOnDismissWhenCancelButtonClicked() {
        var dismissed = false
        composeTestRule.setContent {
            LoadingDialog(onDismiss = { dismissed = true })
        }
        composeTestRule.onNodeWithTag(TestTags.LoadingDialog.CANCEL_BUTTON).performClick()
        assert(dismissed)
    }
}