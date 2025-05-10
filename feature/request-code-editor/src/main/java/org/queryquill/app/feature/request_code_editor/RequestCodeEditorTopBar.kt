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

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import org.queryquill.app.core.ui.QueryQuillTopBar
import org.queryquill.app.feature.request_code_editor.util.TestTags

@Composable
internal fun RequestCodeEditorTopBar(
    stringLanguageType: String, navigateUp: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    QueryQuillTopBar(title = {
        Text(
            text = "Text/${stringLanguageType}", modifier = Modifier.testTag(
                TestTags.TOP_BAR_NAME
            )
        )
    }, navigationIcon = {
        TextButton(onClick = {
            keyboardController?.hide()
            navigateUp()
        }, modifier = Modifier.testTag(TestTags.DONE_BUTTON)) {
            Text(text = stringResource(R.string.done))
        }
    })
}