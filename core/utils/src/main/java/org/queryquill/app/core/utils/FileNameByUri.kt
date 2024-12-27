package org.queryquill.app.core.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun fileNameByUri(resolver: ContentResolver, uri: Uri): String {
    val returnCursor = resolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}