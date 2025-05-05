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

package org.queryquill.app.feature.new_transaction

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.designsystem.QueryQuillTheme

@Composable
fun AddTransactionDialog(onDismiss: () -> Unit) {
    val vm = koinViewModel<NewTransactionViewModel>()
    AddTransactionDialog(onDismiss, vm::addNewTransaction)
}

@Composable
private fun AddTransactionDialog(onDismiss: () -> Unit, addNewTransaction: (String) -> Unit) {
    var label by remember {
        mutableStateOf("New Request")
    }
    AlertDialog(title = {
        Text(
            text = stringResource(id = R.string.add_request),
            style = MaterialTheme.typography.titleLarge
        )
    }, onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = {
            addNewTransaction(label)
            onDismiss()
        }, enabled = label.isNotEmpty()) {
            Text(text = stringResource(id = R.string.add_request))
        }
    }, dismissButton = {
        TextButton(onClick = { onDismiss() }) {
            Text(text = stringResource(R.string.cancel))
        }
    }, text = {
        OutlinedTextField(
            value = label,
            onValueChange = { newLabel ->
                label = newLabel
            },
            label = { Text(text = stringResource(R.string.label)) },
            textStyle = MaterialTheme.typography.titleMedium
        )
    })
}

@Preview
@Composable
private fun PreviewAddTransactionDialog() {
    QueryQuillTheme {
        AddTransactionDialog(onDismiss = {}, addNewTransaction = {})
    }
}