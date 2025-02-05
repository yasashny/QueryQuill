package org.queryquill.app.core.network.utils

import io.ktor.http.Headers
import io.ktor.util.flattenEntries
import org.queryquill.app.core.model.KeyValue

internal fun extractHeaders(headers: Headers): List<KeyValue> {
    return headers.flattenEntries().map {
        KeyValue(
            it.first, it.second
        )
    }
}