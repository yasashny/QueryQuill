package com.yas.request.alertDialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.yas.model.BasicState
import com.yas.request.R

@Composable
internal fun ChangeTypeAlertDialog(
    openDialog: MutableState<Pair<Boolean, BasicState>>,
    title: String,
    updateRequest: (BasicState) -> Unit
) {
    if (openDialog.value.first) {
        AlertDialog(onDismissRequest = {
            openDialog.value = Pair(false, openDialog.value.second)
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
                updateRequest(openDialog.value.second)
                openDialog.value = Pair(false, openDialog.value.second)
            }) {
                Text(stringResource(id = R.string.ok))
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = Pair(false, openDialog.value.second)
            }) {
                Text(stringResource(id = R.string.cancel))
            }
        })
    }
}