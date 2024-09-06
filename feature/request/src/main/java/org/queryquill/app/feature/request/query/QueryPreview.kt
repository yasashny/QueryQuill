package org.queryquill.app.feature.request.query

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.KeyValue

internal fun LazyListScope.queryPreview(
    getUrl: () -> String, getQuery: () -> ImmutableList<KeyValue>
) {
    item {
        val url = getUrl()
        val query = getQuery()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(15.dp)
        ) {
            val pattern = Regex(".*\\?.*=.*")
            val text = "${
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    "http://"

                } else {
                    ""
                }
            }${url}${
                if (pattern.containsMatchIn(url)) {
                    "&"
                } else {
                    if (query.list.any { keyValue: KeyValue -> keyValue != KeyValue.empty() }) {
                        "?"
                    } else {
                        ""
                    }
                }
            }${
                query.list.filter { keyValue: KeyValue -> keyValue != KeyValue.empty() }
                    .joinToString(separator = "&") { keyValue: KeyValue -> "${keyValue.key}=${keyValue.value}" }
            }"
            Text(text = text, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }

    }
}