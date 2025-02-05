package org.queryquill.app.core.network.prepareRequest

import io.ktor.client.request.HttpRequestBuilder
import org.queryquill.app.core.model.KeyValue

internal fun HttpRequestBuilder.applyUrlParameters(list: List<KeyValue>) {
    url {
        list.forEach { keyValue ->
            if (keyValue != KeyValue.empty()) {
                parameters.append(keyValue.key, keyValue.value)
            }
        }
    }
}