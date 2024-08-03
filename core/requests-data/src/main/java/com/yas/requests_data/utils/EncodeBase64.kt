package com.yas.requests_data.utils

internal fun encodeBase64(value: String): String {
    return java.util.Base64.getEncoder().encodeToString(value.toByteArray())
}