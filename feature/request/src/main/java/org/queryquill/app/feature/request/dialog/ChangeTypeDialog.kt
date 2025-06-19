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
internal fun ChangeTypeDialog(
    title: String, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = {
        onDismiss()
    }, title = {
        Text(text = stringResource(R.string.switch_type, title))
    }, text = {
        Text(
            text = stringResource(
                R.string.current_will_be_lost_are_you_sure_you_want_to_continue, title
            )
        )
    }, confirmButton = {
        TextButton(
            modifier = Modifier.testTag(TestTags.ChangeTypeDialog.CONFIRM_BUTTON), onClick = {
                onConfirm()
            }) {
            Text(stringResource(id = R.string.ok))
        }
    }, dismissButton = {
        TextButton(
            modifier = Modifier.testTag(TestTags.ChangeTypeDialog.DISMISS_BUTTON), onClick = {
                onDismiss()
            }) {
            Text(stringResource(id = R.string.cancel))
        }
    })
}

@Preview
@Composable
private fun PreviewChangeTypeDialog() {
    QueryQuillTheme(dynamicColor = false) {
        ChangeTypeDialog(title = "body", onDismiss = {}, onConfirm = {})
    }
}