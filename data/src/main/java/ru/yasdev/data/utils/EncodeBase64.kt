package ru.yasdev.data.utils

fun encodeBase64(value: String): String {
    return java.util.Base64.getEncoder().encodeToString(value.toByteArray())
}