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