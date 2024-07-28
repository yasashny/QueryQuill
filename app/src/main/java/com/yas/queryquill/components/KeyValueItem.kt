package com.yas.queryquill.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.domain.requestsDb.models.KeyValue

@Composable
fun KeyValueItem(
    keyValue: KeyValue,
    onTextChanged: (KeyValue) -> Unit,
    modifier: Modifier = Modifier,
    deleteItem: () -> Unit = {},
    deleteButtonEnabled: () -> Boolean = { false },
    isDeleteButtonVisible: Boolean = true
) {
    OutlinedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = keyValue.key,
                    onValueChange = {
                        onTextChanged(KeyValue(it, keyValue.value))
                    },
                    label = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                if (isDeleteButtonVisible) {
                    FilledTonalIconButton(
                        onClick = { deleteItem() },
                        enabled = deleteButtonEnabled(),
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                    }
                }
            }

            OutlinedTextField(
                value = keyValue.value,
                onValueChange = {
                    onTextChanged(KeyValue(keyValue.key, it))
                },
                label = { Text(text = "Value") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}