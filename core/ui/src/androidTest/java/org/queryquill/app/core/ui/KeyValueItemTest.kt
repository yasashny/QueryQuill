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

package org.queryquill.app.core.ui

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.model.KeyValue

class KeyValueItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenKeyValueItemCreated_shouldDisplayInitialValues() {
        val initialKey = "Test Key"
        val initialValue = "Test Value"
        val keyValue = KeyValue(initialKey, initialValue)
        composeTestRule.setContent {
            KeyValueItem(keyValue = keyValue, onTextChanged = {}, deleteItem = {})
        }
        composeTestRule.onNodeWithText(initialKey).assertExists()
        composeTestRule.onNodeWithText(initialValue).assertExists()
    }

    @Test
    fun whenDeleteButtonEnabled_shouldBeClickable() {
        val keyValue = KeyValue("Key", "Value")
        composeTestRule.setContent {
            KeyValueItem(
                keyValue = keyValue,
                onTextChanged = {},
                deleteItem = {},
                deleteButtonEnabled = { true })
        }
        composeTestRule.onNodeWithTag("Delete Button").assertExists().assertIsEnabled()
    }

    @Test
    fun whenDeleteButtonDisabled_shouldNotBeClickable() {
        val keyValue = KeyValue("Key", "Value")
        composeTestRule.setContent {
            KeyValueItem(
                keyValue = keyValue,
                onTextChanged = {},
                deleteItem = {},
                deleteButtonEnabled = { false })
        }
        composeTestRule.onNodeWithTag("Delete Button").assertExists().assertIsNotEnabled()
    }

    @Test
    fun whenDeleteButtonNotVisible_shouldNotExist() {
        val keyValue = KeyValue("Key", "Value")
        composeTestRule.setContent {
            KeyValueItem(
                keyValue = keyValue,
                onTextChanged = {},
                deleteItem = {},
                isDeleteButtonVisible = false
            )
        }
        composeTestRule.onNodeWithTag("Delete Button").assertDoesNotExist()
    }

    @Test
    fun whenKeyChanged_shouldTriggerCallback() {
        val keyValue = KeyValue("Initial Key", "Value")
        var lastKeyValue: KeyValue? = null
        composeTestRule.setContent {
            KeyValueItem(
                keyValue = keyValue,
                onTextChanged = { lastKeyValue = it },
                deleteItem = {})
        }
        composeTestRule.onNodeWithText("Initial Key").performTextInput("New")
        assert(lastKeyValue?.key?.contains("New") == true)
    }

    @Test
    fun whenCustomLabelsProvided_shouldDisplayCustomLabels() {
        val keyValue = KeyValue("Key", "Value")
        val customText1 = "Custom Name"
        val customText2 = "Custom Value"
        composeTestRule.setContent {
            KeyValueItem(
                keyValue = keyValue,
                onTextChanged = {},
                deleteItem = {},
                text1 = customText1,
                text2 = customText2
            )
        }
        composeTestRule.onNodeWithText(customText1).assertExists()
        composeTestRule.onNodeWithText(customText2).assertExists()
    }
}