package ru.yasdev.queryquill.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.KeyValue


fun LazyListScope.editableList(
    items: List<KeyValue>,
    updateRequest: (items: List<KeyValue>) -> Unit
) {
    itemsIndexed(items) { index, item ->
        EditableListItem(keyValue = item, onTextChanged = { listItem ->
            val updatedItems = items.toMutableList()
            updatedItems[index] = listItem
            updateRequest(updatedItems)
            if (index == items.size - 1) {
                val newItemList = updatedItems.toMutableList()
                newItemList.add(KeyValue("", ""))
                updateRequest(newItemList)
            }
        }, deleteItem = {
            val updatedItems = items.toMutableList()
            updatedItems.removeAt(index)
            updateRequest(updatedItems)
        },
            deleteButtonEnabled = { items.size - 1 != index })


    }

}

@Composable
fun EditableListItem(
    keyValue: KeyValue,
    onTextChanged: (KeyValue) -> Unit,
    deleteItem: () -> Unit,
    deleteButtonEnabled: () -> Boolean
) {

    Row {
        OutlinedTextField(
            value = keyValue.key, onValueChange = {
                onTextChanged(KeyValue(it, keyValue.value))
            },
            label = { Text(text = "Name") }, modifier = Modifier
                .padding(start = 15.dp, top = 15.dp)
                .weight(1f)
        )
        OutlinedTextField(value = keyValue.value, trailingIcon = {
            IconButton(onClick = { deleteItem() }, enabled = deleteButtonEnabled()) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
            }
        }, onValueChange = {
            onTextChanged(KeyValue(keyValue.key, it))
        },
            label = { Text(text = "Value") }, modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .weight(2f)
        )
    }


}