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

package org.queryquill.app.feature.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.feature.settings.util.TestTags

class SettingsDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun settingsDialog_DisplaysLoadingIndicator() {
        composeTestRule.setContent {
            QueryQuillTheme {
                SettingsDialog(settingsState = SettingsUiState.Loading,
                    onDismiss = {},
                    updateModel = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTags.CIRCULAR_PROGRESS_INDICATOR)
            .assertIsDisplayed()
    }
}