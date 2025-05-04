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