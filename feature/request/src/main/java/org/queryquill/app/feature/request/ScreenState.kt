package org.queryquill.app.feature.request

internal enum class ScreenState(val title: String) {
    BODY("Body"), AUTH("Auth"), HEADER("Header"), QUERY("Query")
}