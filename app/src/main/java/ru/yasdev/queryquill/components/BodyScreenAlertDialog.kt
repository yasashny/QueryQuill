package ru.yasdev.queryquill.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun BodyScreenAlertDialog(isChangeBodyState: MutableState<Pair<Boolean, Int>>,
                          openDialog: MutableState<Pair<Boolean, Int>>) {

    if (openDialog.value.first) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = Pair(false, openDialog.value.second)
            },
            title = {
                Text(text = "Switch Body Type?")
            },
            text = {
                Text(text = "Current body will be lost. Are you sure you want to continue?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isChangeBodyState.value = Pair(true, openDialog.value.second)
                        openDialog.value = Pair(false, openDialog.value.second)
                    }
                ) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = Pair(false, openDialog.value.second)
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}