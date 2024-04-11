package ru.yasdev.queryquill.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ru.yasdev.domain.requestsDb.models.AuthState

@Composable
fun AuthScreenAlertDialog(
    openDialog: MutableState<Pair<Boolean, AuthState>>,
    updateRequest: (AuthState) -> Unit
) {
    if (openDialog.value.first) {
        AlertDialog(onDismissRequest = {
            openDialog.value = Pair(false, openDialog.value.second)
        }, title = {
            Text(text = "Switch Auth Type?")
        }, text = {
            Text(text = "Current auth will be lost. Are you sure you want to continue?")
        }, confirmButton = {
            TextButton(onClick = {
                updateRequest(openDialog.value.second)
                openDialog.value = Pair(false, openDialog.value.second)
            }) {
                Text("Ok")
            }
        }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = Pair(false, openDialog.value.second)
            }) {
                Text("Cancel")
            }
        })
    }
}