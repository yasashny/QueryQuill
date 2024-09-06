package org.queryquill.app.feature.request.alertDialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.queryquill.app.feature.request.R

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
        TextButton(onClick = {
            onConfirm()
        }) {
            Text(stringResource(R.string.ok))
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismiss()
        }) {
            Text(stringResource(R.string.cancel))
        }
    })
}