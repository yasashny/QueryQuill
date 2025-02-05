package org.queryquill.app.core.network.utils

import io.ktor.client.statement.HttpResponse

internal fun calculateElapsedTime(response: HttpResponse): String {
    val elapsed = response.responseTime.timestamp - response.requestTime.timestamp
    return elapsed.toString()
}