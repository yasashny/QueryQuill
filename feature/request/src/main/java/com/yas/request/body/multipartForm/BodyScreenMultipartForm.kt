package com.yas.request.body.multipartForm

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.model.KeyValue
import com.yas.model.MultipartFormState


internal fun LazyListScope.bodyScreenMultipartForm(
    items: List<MultipartFormState>, updateMultipartForm: (List<MultipartFormState>) -> Unit
) {
    item {
        Spacer(modifier = Modifier.padding(top = 18.dp))
    }
    itemsIndexed(items) { index, item ->
        MultipartFormListItem(multipartFormState = item, onTextChanged = { listItem, flag ->
            val updatedItems = items.toMutableList()
            updatedItems[index] = listItem
            updateMultipartForm(updatedItems)
            if ((index == items.size - 1) and flag) {
                val newItemList = updatedItems.toMutableList()
                newItemList.add(MultipartFormState.Text(KeyValue.empty()))
                updateMultipartForm(newItemList)
            }
        }, deleteItem = {
            val updatedItems = items.toMutableList()
            updatedItems.removeAt(index)
            updateMultipartForm(updatedItems)
        }, deleteButtonEnabled = { items.size - 1 != index })
    }
}