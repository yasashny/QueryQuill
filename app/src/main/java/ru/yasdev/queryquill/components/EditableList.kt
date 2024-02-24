package ru.yasdev.queryquill.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.ListItem

@Composable
fun EditableList(items: List<ListItem>, onValueChanged: (items: List<ListItem>) -> Unit) {

    LazyColumn {
        itemsIndexed(items) { index, item ->
            EditableListItem(listItem = item, onTextChanged = { listItem ->
                val updatedItems = items.toMutableList()
                updatedItems[index] = listItem
                onValueChanged(updatedItems)
                if (index == items.size - 1) {
                    val newItemList = updatedItems.toMutableList()
                    newItemList.add(ListItem("", ""))
                    onValueChanged(newItemList)
                }
            }, deleteItem = {
                val updatedItems = items.toMutableList()
                updatedItems.removeAt(index)
                onValueChanged(updatedItems)
            },
                deleteButtonEnabled = {if (items.size - 1 == index) false else true })


        }
    }
}

@Composable
fun EditableListItem(
    listItem: ListItem, onTextChanged: (ListItem) -> Unit, deleteItem: () -> Unit, deleteButtonEnabled: () -> Boolean
) {

    Row {
        OutlinedTextField(
            value = listItem.name, onValueChange = {
                onTextChanged(ListItem(it, listItem.value))
            },
            label = { Text(text = "Name")}
            , modifier = Modifier
                .padding(start = 15.dp, top = 15.dp)
                .weight(1f)
        )
        OutlinedTextField(value = listItem.value, trailingIcon = {
            IconButton(onClick = { deleteItem() }, enabled = deleteButtonEnabled()) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
            }
        }, onValueChange = {
            onTextChanged(ListItem(listItem.name, it))
        },
            label = { Text(text = "Value")}
            , modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .weight(2f)
        )
    }


}