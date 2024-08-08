package com.yas.request.requestScreenHeader

internal enum class HttpRequestHeaderState(val title: String) {
    BODY("Body"),
    AUTH("Auth"),
    HEADER("Header"),
    QUERY("Query")
}