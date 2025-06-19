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

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.utils.TestTags

@Composable
internal fun ChangeContentTypeDialog(
    newContentType: String, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, title = {
        Text(text = stringResource(R.string.change_content_type))
    }, text = {
        Text(
            text = stringResource(
                R.string.do_you_want_set_the_content_type_header_to, newContentType
            )
        )
    }, confirmButton = {
        TextButton(
            modifier = Modifier.testTag(TestTags.ChangeContentTypeDialog.CONFIRM_BUTTON),
            onClick = {
                onConfirm()
            }) {
            Text(stringResource(R.string.ok))
        }
    }, dismissButton = {
        TextButton(
            modifier = Modifier.testTag(TestTags.ChangeContentTypeDialog.DISMISS_BUTTON),
            onClick = {
                onDismiss()
            }) {
            Text(stringResource(R.string.cancel))
        }
    })
}

@Preview
@Composable
private fun PreviewChangeContentTypeDialog() {
    QueryQuillTheme(dynamicColor = false) {
        ChangeContentTypeDialog(newContentType = "application/json", onDismiss = {}, onConfirm = {})
    }
}
