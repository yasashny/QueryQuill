package org.queryquill.app.core.network.prepareRequest

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import org.queryquill.app.core.model.KeyValue

internal fun HttpRequestBuilder.applyHeaders(list: List<KeyValue>) {
    headers {
        list.forEach { keyValue ->
            if (keyValue != KeyValue.empty()) {
                append(keyValue.key, keyValue.value)
            }
        }
    }
}