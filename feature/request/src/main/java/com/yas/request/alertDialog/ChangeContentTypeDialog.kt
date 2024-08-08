package com.yas.request.alertDialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.yas.request.R

@Composable
internal fun ChangeContentTypeDialog(
    openDialog: MutableState<Pair<Boolean, String>>, isChangeType: MutableState<Boolean>
) {
    if (openDialog.value.first) {
        AlertDialog(onDismissRequest = {
            openDialog.value = Pair(false, "")
        }, title = {
            Text(text = stringResource(R.string.change_content_type))
        }, text = {
            Text(
                text = stringResource(
                    R.string.do_you_want_set_the_content_type_header_to, openDialog.value.second
                )
            )
        }, confirmButton = {
            TextButton(onClick = {
                isChangeType.value = true
                openDialog.value = Pair(false, openDialog.value.second)
            }) {
                Text(stringResource(R.string.ok))
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = Pair(false, openDialog.value.second)
            }) {
                Text(stringResource(R.string.cancel))
            }
        })
    }
}