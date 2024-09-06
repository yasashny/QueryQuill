package org.queryquill.app.feature.request.alertDialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.queryquill.app.feature.request.R

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
        TextButton(onClick = {
            onConfirm()
        }) {
            Text(stringResource(id = R.string.ok))
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismiss()
        }) {
            Text(stringResource(id = R.string.cancel))
        }
    })
}