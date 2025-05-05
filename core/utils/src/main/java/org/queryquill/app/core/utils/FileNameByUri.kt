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

package org.queryquill.app.core.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File


/**
 * Extracts the file name from the given URI based on its scheme.
 *
 * @param resolver The content resolver used to query the URI.
 * @param uri The URI of the file whose name is to be determined.
 * @return The file name as a string. Returns an empty string if the file name cannot be determined.
 */
fun fileNameByUri(resolver: ContentResolver, uri: Uri): String {
    return when (uri.scheme) {
        ContentResolver.SCHEME_FILE -> {
            uri.path?.let { File(it).name } ?: ""
        }

        ContentResolver.SCHEME_CONTENT -> {
            val returnCursor = resolver.query(uri, null, null, null, null)!!
            val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            val name: String = returnCursor.getString(nameIndex)
            returnCursor.close()
            name
        }

        else -> ""
    }
}