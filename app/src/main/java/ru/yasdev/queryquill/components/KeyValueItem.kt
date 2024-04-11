package ru.yasdev.queryquill.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.KeyValue

@Composable
fun KeyValueItem(
    keyValue: KeyValue,
    onTextChanged: (KeyValue) -> Unit,
    deleteItem: () -> Unit = {},
    deleteButtonEnabled: () -> Boolean = {false},
    isDeleteButtonVisible: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = keyValue.key, onValueChange = {
                    onTextChanged(KeyValue(it, keyValue.value))
                },
                label = { Text(text = "Name") }, modifier = Modifier
                    .weight(1f)
            )
            OutlinedTextField(value = keyValue.value, trailingIcon = {
                if(isDeleteButtonVisible){
                    IconButton(onClick = { deleteItem() }, enabled = deleteButtonEnabled()) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                    }
                }
            }, onValueChange = {
                onTextChanged(KeyValue(keyValue.key, it))
            },
                label = { Text(text = "Value") }, modifier = Modifier
                    .padding(start = 15.dp)
                    .weight(2f)
            )
        }
    }




}