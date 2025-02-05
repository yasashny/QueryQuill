package org.queryquill.app.core.network.utils

internal fun formatUrl(url: String): String {
    return if (url.startsWith("http://") || url.startsWith("https://")) {
        url
    } else {
        "http://$url"
    }
}