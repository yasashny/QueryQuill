package com.yas.queryquill.screens.requestScreens.httpRequestScreen.body.multipartForm

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.states.BodyState
import com.yas.domain.requestsDb.states.MultipartFormState


fun LazyListScope.bodyScreenMultipartForm(
    items: List<MultipartFormState>, updateRequest: (BodyState.MultipartForm) -> Unit
) {
    item {
        Spacer(modifier = Modifier.padding(top = 18.dp))
    }
    itemsIndexed(items) { index, item ->
        MultipartFormListItem(multipartFormState = item, onTextChanged = { listItem, flag ->
            val updatedItems = items.toMutableList()
            updatedItems[index] = listItem
            updateRequest(BodyState.MultipartForm(updatedItems))
            if ((index == items.size - 1) and flag) {
                val newItemList = updatedItems.toMutableList()
                newItemList.add(MultipartFormState.Text(KeyValue("", "")))
                updateRequest(BodyState.MultipartForm(newItemList))
            }
        }, deleteItem = {
            val updatedItems = items.toMutableList()
            updatedItems.removeAt(index)
            updateRequest(BodyState.MultipartForm(updatedItems))
        }, deleteButtonEnabled = { items.size - 1 != index })
    }
}