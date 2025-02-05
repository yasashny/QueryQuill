package org.queryquill.app.core.network.prepareRequest

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpMethod
import org.queryquill.app.core.model.HttpType

internal fun HttpRequestBuilder.applyMethod(httpType: HttpType) {
    method = when (httpType) {
        HttpType.GET -> HttpMethod.Get
        HttpType.POST -> HttpMethod.Post
        HttpType.PUT -> HttpMethod.Put
        HttpType.PATCH -> HttpMethod.Patch
        HttpType.DELETE -> HttpMethod.Delete
        HttpType.OPTIONS -> HttpMethod.Options
        HttpType.HEAD -> HttpMethod.Head
    }
}