package com.yas.request.query

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.model.ImmutableList
import com.yas.model.KeyValue
import com.yas.request.components.editableList


internal fun LazyListScope.queryScreen(
    getQuery: () -> ImmutableList<KeyValue>,
    getUrl: () -> String,
    updateQuery: (List<KeyValue>) -> Unit
) {
    queryPreview(getUrl = getUrl, getQuery = getQuery)
    editableList(items = getQuery().list) { keyValueList ->
        updateQuery(keyValueList)
    }
}