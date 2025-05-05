/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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