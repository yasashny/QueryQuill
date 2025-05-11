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

package org.queryquill.app.feature.request_code_editor

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.test.platform.app.InstrumentationRegistry
import io.github.rosemoe.sora.text.Content
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.model.TextType
import org.queryquill.app.core.ui.rememberCodeEditorState
import org.queryquill.app.feature.request_code_editor.util.TestTags
import java.io.File

class RequestCodeEditorScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    @Test
    fun testRequestCodeEditorScreen_initializesFileIfNotExist() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val fileName = "testFile.txt"
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            file.delete()
        }
        composeTestRule.setContent {
            RequestCodeEditorScreen(
                fileName = fileName, textType = TextType.PLAIN, navigateUp = {})
        }
        composeTestRule.waitForIdle()
        assert(file.exists()) { "File should be created if it does not exist." }
        assert(file.readText() == "") { "File content should be empty initially." }
        file.delete()
    }

    @Test
    fun testRequestCodeEditorScreen_savesDataOnStop() = runTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val fileName = "testSaveFile.txt"
        val file = File(context.filesDir, fileName)
        val initialContent = "Initial Content"
        if (file.exists()) {
            file.delete()
        }
        file.writeText(initialContent)
        composeTestRule.setContent {
            val state = rememberCodeEditorState().apply {
                content = Content("Updated Content")
            }
            RequestCodeEditorScreen(
                fileName = fileName, textType = TextType.PLAIN, navigateUp = {}, state = state
            )
        }
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.CREATED)
        composeTestRule.waitForIdle()
        assert(file.readText() == "Updated Content") { "File content was not updated correctly." }
        file.delete()
    }

    @Test
    fun testNavigateUp() {
        var navigateUpCalled = false
        composeTestRule.setContent {
            RequestCodeEditorScreen(
                fileName = "test.txt",
                textType = TextType.PLAIN,
                navigateUp = { navigateUpCalled = true })
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TestTags.TOP_BAR_NAME).assertExists()
        composeTestRule.onNodeWithTag(TestTags.DONE_BUTTON).assertExists()
        composeTestRule.onNodeWithTag(TestTags.DONE_BUTTON).performClick()
        assert(navigateUpCalled) { "Navigate up should be called when back button is clicked" }
    }

    @Test
    fun testTopBarLabel() {
        composeTestRule.setContent {
            RequestCodeEditorScreen(
                fileName = "test.txt", textType = TextType.PLAIN, navigateUp = {})
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Text/PLAIN").assertExists()
    }
}