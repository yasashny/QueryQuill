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

package org.queryquill.app.feature.response.utils

import android.os.Build
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.LanguageType


/**
 * Converts a given ContentType to its corresponding LanguageType based on platform API level compatibility.
 *
 * @param contentType The type of content (e.g., text, image, application) to be converted to a LanguageType.
 * @return The corresponding LanguageType for the provided ContentType. If the ContentType
 * is not supported or compatible with the platform's API level, it returns LanguageType.OTHER.
 */
internal fun contentTypeToLanguageType(contentType: ContentType): LanguageType {
    return when (contentType) {
        ContentType.Text.HTML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.HTML
        } else {
            LanguageType.OTHER
        }

        ContentType.Image.JPEG -> LanguageType.OTHER
        ContentType.Application.JSON -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.JSON
        } else {
            LanguageType.OTHER
        }

        ContentType.Text.PLAIN -> LanguageType.PLAIN
        ContentType.Image.PNG -> LanguageType.OTHER
        ContentType.Image.WEBP -> LanguageType.OTHER
        ContentType.Text.XML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.XML
        } else {
            LanguageType.OTHER
        }

        ContentType.Image.BMP -> LanguageType.OTHER
        ContentType.Image.HEIC -> LanguageType.OTHER
        ContentType.Image.HEIF -> LanguageType.OTHER
    }
}