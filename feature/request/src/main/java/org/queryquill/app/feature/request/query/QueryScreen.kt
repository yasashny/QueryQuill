package org.queryquill.app.feature.request.query

import androidx.compose.foundation.lazy.LazyListScope
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.feature.request.components.editableList


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