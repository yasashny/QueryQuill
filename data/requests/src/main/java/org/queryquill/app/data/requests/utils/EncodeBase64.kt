package org.queryquill.app.data.requests.utils

internal fun encodeBase64(value: String): String {
    return java.util.Base64.getEncoder().encodeToString(value.toByteArray())
}