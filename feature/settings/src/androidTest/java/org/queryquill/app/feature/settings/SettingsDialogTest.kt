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

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.core.model.ThemeState
import org.queryquill.app.feature.settings.util.TestTags

class SettingsDialogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun settingsDialog_DisplaysLoadingState() {
        composeTestRule.setContent {
            QueryQuillTheme {
                SettingsDialog(
                    onDismiss = {},
                    settingsState = SettingsUiState.Loading,
                    updateModel = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTags.CIRCULAR_PROGRESS_INDICATOR).assertExists()
    }

    @Test
    fun settingsDialog_DisplaysSuccessStateWithThemeOptions() {
        composeTestRule.setContent {
            QueryQuillTheme {
                SettingsDialog(
                    onDismiss = {}, settingsState = SettingsUiState.Success(
                        SettingsModel(ThemeState.LIGHT)
                    ), updateModel = {})
            }
        }

        composeTestRule.onNodeWithTag(TestTags.THEME_SECTION).assertExists()
        composeTestRule.onNodeWithText(ThemeState.SYSTEM.title).assertExists()
        composeTestRule.onNodeWithText(ThemeState.DARK.title).assertExists()
        composeTestRule.onNodeWithText(ThemeState.LIGHT.title).assertExists()
    }

    @Test
    fun settingsDialog_SourceCodeButtonLaunchesLicensesActivity() {
        val fakeContext = mutableStateOf<Context?>(null)
        composeTestRule.setContent {
            fakeContext.value = LocalContext.current
            QueryQuillTheme {
                SettingsDialog(
                    onDismiss = {}, settingsState = SettingsUiState.Success(
                        SettingsModel(ThemeState.LIGHT)
                    ), updateModel = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTags.SOURCE_CODE_BUTTON).performClick()

        val intent = Intent(fakeContext.value, OssLicensesMenuActivity::class.java)
        Assert.assertEquals(intent.component?.className, OssLicensesMenuActivity::class.java.name)
    }

    @Test
    fun settingsDialog_PrivacyPolicyButtonOpensUri() {
        composeTestRule.setContent {
            QueryQuillTheme {
                SettingsDialog(
                    onDismiss = {}, settingsState = SettingsUiState.Success(
                        SettingsModel(ThemeState.LIGHT)
                    ), updateModel = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTags.PRIVACY_POLICY_BUTTON).assertHasClickAction()
    }

    @Test
    fun settingsDialog_FeedbackButtonCopiesToClipboard() {
        val fakeClipboardManager = mutableStateOf<ClipboardManager?>(null)

        composeTestRule.setContent {
            QueryQuillTheme {
                val context = LocalContext.current
                fakeClipboardManager.value =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                SettingsDialog(
                    onDismiss = {}, settingsState = SettingsUiState.Success(
                        SettingsModel(ThemeState.LIGHT)
                    ), updateModel = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTags.FEEDBACK_BUTTON).performClick()
        val clipboard = fakeClipboardManager.value
        val clipData = clipboard?.primaryClip
        Assert.assertNotNull(clipData)
        Assert.assertEquals(CONTACT_EMAIL, clipData?.getItemAt(0)?.text.toString())
    }

    @Test
    fun settingsDialog_AppVersionTextIsDisabled() {
        composeTestRule.setContent {
            QueryQuillTheme {
                SettingsDialog(
                    onDismiss = {}, settingsState = SettingsUiState.Success(
                        SettingsModel(ThemeState.LIGHT)
                    ), updateModel = {})
            }
        }
        composeTestRule.onNodeWithTag(TestTags.VERSION_BUTTON).assertExists()
        composeTestRule.onNodeWithTag(TestTags.VERSION_BUTTON).assertIsNotEnabled()
    }
}