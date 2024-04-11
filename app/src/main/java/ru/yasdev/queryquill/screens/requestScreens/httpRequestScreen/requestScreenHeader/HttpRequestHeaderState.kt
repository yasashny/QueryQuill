package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.requestScreenHeader

enum class HttpRequestHeaderState(val title: String) {
    BODY("Body"),
    AUTH("Auth"),
    HEADER("Header"),
    QUERY("Query")
}