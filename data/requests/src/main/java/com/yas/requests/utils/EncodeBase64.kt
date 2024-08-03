package com.yas.requests.utils

internal fun encodeBase64(value: String): String {
    return java.util.Base64.getEncoder().encodeToString(value.toByteArray())
}