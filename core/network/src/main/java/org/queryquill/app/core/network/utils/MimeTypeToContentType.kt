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

package org.queryquill.app.core.network.utils

import org.queryquill.app.core.model.ContentType

internal fun mimeTypeToContentType(mimeType: String?): ContentType {
    return when (mimeType) {
        "text/html" -> ContentType.Text.HTML
        "text/plain" -> ContentType.Text.PLAIN
        "text/xml" -> ContentType.Text.XML
        "application/json" -> ContentType.Application.JSON
        "image/jpeg" -> ContentType.Image.JPEG
        "image/png" -> ContentType.Image.PNG
        "image/webp" -> ContentType.Image.WEBP
        "image/bmp" -> ContentType.Image.BMP
        "image/heif" -> ContentType.Image.HEIF
        "image/heic" -> ContentType.Image.HEIC
        else -> ContentType.Text.PLAIN
    }
}