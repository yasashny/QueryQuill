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

package org.queryquill.app.feature.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.feature.transaction.util.TestTags

@Composable
internal fun ChangeLabelAlertDialog(
    onDismiss: () -> Unit, onConfirm: (newLabel: String) -> Unit
) {
    var newLabel by remember {
        mutableStateOf("")
    }
    AlertDialog(title = {
        Text(
            text = stringResource(id = R.string.change_label),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.testTag(TestTags.ChangeLabelDialog.LABEL_INPUT)
        )
    }, onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(
            onClick = { onConfirm(newLabel) },
            enabled = newLabel.isNotEmpty(),
            modifier = Modifier.testTag(TestTags.ChangeLabelDialog.CONFIRM_BUTTON)
        ) {
            Text(text = stringResource(id = R.string.change_label))
        }
    }, dismissButton = {
        TextButton(
            onClick = { onDismiss() },
            modifier = Modifier.testTag(TestTags.ChangeLabelDialog.CANCEL_BUTTON)
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
    }, text = {
        Column {
            OutlinedTextField(
                value = newLabel,
                onValueChange = { newLabel = it },
                label = { Text(text = stringResource(R.string.label)) },
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.testTag(TestTags.ChangeLabelDialog.LABEL_TEXT)
            )
        }
    })
}

@Preview
@Composable
private fun PreviewChangeLabelAlertDialog() {
    ChangeLabelAlertDialog(onDismiss = {}, onConfirm = {})
}