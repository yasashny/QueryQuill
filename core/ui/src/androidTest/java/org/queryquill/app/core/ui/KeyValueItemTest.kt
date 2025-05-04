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