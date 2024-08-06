package com.yas.queryquill.screens.requestScreens.httpRequestScreen.query

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
import com.yas.model.KeyValue
import com.yas.model.RequestModel

fun LazyListScope.queryPreview(requestModel: RequestModel) {
    item {
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
                if (!requestModel.url.startsWith("http://") && !requestModel.url.startsWith("https://")) {
                    "http://"

                } else {
                    ""
                }
            }${requestModel.url}${
                if (pattern.containsMatchIn(requestModel.url)) {
                    "&"
                } else {
                    if (requestModel.query.list.any { keyValue: KeyValue -> keyValue != KeyValue.empty() }) {
                        "?"
                    } else {
                        ""
                    }
                }
            }${
                requestModel.query.list.filter { keyValue: KeyValue -> keyValue != KeyValue.empty() }
                    .joinToString(separator = "&") { keyValue: KeyValue -> "${keyValue.key}=${keyValue.value}" }
            }"
            Text(text = text, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }

    }
}