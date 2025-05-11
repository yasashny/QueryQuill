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

package org.queryquill.app.feature.request_code_editor

import android.os.Build
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.core.model.TextType

internal fun TextType.toLanguageType() = when (this) {
    TextType.JSON -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LanguageType.JSON
    } else {
        LanguageType.OTHER
    }

    TextType.XML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LanguageType.XML
    } else {
        LanguageType.OTHER
    }

    TextType.PLAIN -> LanguageType.PLAIN
    TextType.OTHER -> LanguageType.OTHER
}