package org.queryquill.app.core.network.utils

import java.util.Base64

internal fun encodeBase64(value: String): String {
    return Base64.getEncoder().encodeToString(value.toByteArray())
}