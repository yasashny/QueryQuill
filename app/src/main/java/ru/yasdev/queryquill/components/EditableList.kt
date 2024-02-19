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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.utils.ListItem

@Composable
fun EditableList(items: List<ListItem>) {
    var editedItems by remember { mutableStateOf(items) }

    LazyColumn {
        itemsIndexed(editedItems) { index, item ->
            EditableListItem(
                text = item,
                onTextChanged = { newText ->
                    editedItems = editedItems.toMutableList().also {
                        it[index] = newText
                    }
                    if (index == editedItems.size - 1){
                        Log.d("q", "q")
                        editedItems = editedItems.toMutableList().apply { add(ListItem("", "")) }
                    }
                    Log.d("sdsds", editedItems.toString())
                },
                deleteItem = {editedItems = editedItems.toMutableList().apply { removeAt(index) }}
            )
        }
    }
}

@Composable
fun EditableListItem(
    text: ListItem,
    onTextChanged: (ListItem) -> Unit,
    deleteItem: () -> Unit
) {
    var item by remember { mutableStateOf(text) }

    Row {
        OutlinedTextField(
            value = item.name,
            onValueChange = {
                item = ListItem(it, item.value)
                onTextChanged(item)
            },
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp)
                .weight(1f)
        )
        OutlinedTextField(
            value = item.value,
            trailingIcon = { IconButton(onClick = { deleteItem() }, enabled = text != ListItem("", "")) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
            }
            },
            onValueChange = {
                item = ListItem(item.name, it)
                onTextChanged(item)
            },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .weight(2f)
        )
    }


}