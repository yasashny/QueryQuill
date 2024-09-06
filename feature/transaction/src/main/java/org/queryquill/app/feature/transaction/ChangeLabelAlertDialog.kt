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
import androidx.compose.ui.res.stringResource

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
            style = MaterialTheme.typography.titleLarge
        )
    }, onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = { onConfirm(newLabel) }, enabled = newLabel.isNotEmpty()) {
            Text(text = stringResource(id = R.string.change_label))
        }
    }, dismissButton = {
        TextButton(onClick = { onDismiss() }) {
            Text(text = stringResource(id = R.string.cancel))
        }
    }, text = {
        Column {
            OutlinedTextField(
                value = newLabel,
                onValueChange = { newLabel = it },
                label = { Text(text = stringResource(R.string.label)) },
                textStyle = MaterialTheme.typography.titleMedium
            )
        }
    })
}