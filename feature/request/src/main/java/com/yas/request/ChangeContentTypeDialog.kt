package com.yas.request

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ChangeContentTypeDialog(
    openDialog: MutableState<Pair<Boolean, String>>, isChangeType: MutableState<Boolean>
) {
    if (openDialog.value.first) {
        AlertDialog(onDismissRequest = {
            openDialog.value = Pair(false, "")
        }, title = {
            Text(text = "Change Content-Type?")
        }, text = {
            Text(text = "Do you want set the Content-Type header to ${openDialog.value.second}?")
        }, confirmButton = {
            TextButton(onClick = {
                isChangeType.value = true
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