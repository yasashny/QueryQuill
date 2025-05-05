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
import org.queryquill.app.core.model.KeyValue

internal fun HttpRequestBuilder.applyUrlParameters(list: List<KeyValue>) {
    url {
        list.forEach { keyValue ->
            if (keyValue != KeyValue.empty()) {
                parameters.append(keyValue.key, keyValue.value)
            }
        }
    }
}