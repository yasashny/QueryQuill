package ru.yasdev.queryquill.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ru.yasdev.domain.requestsDb.models.BasicState

@Composable
fun ChangeTypeAlertDialog(
    openDialog: MutableState<Pair<Boolean, BasicState>>,
    title: String,
    updateRequest: (BasicState) -> Unit
) {
    if (openDialog.value.first) {
        AlertDialog(onDismissRequest = {
            openDialog.value = Pair(false, openDialog.value.second)
        }, title = {
            Text(text = "Switch $title Type?")
        }, text = {
            Text(text = "Current $title will be lost. Are you sure you want to continue?")
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