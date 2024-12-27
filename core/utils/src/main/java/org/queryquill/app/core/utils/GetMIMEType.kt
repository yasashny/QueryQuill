package org.queryquill.app.core.utils

import android.content.Context
import android.net.Uri
import io.ktor.http.ContentType
import io.ktor.http.fromFilePath

fun getMIMEType(context: Context, uri: Uri): String {
    return if (uri != Uri.EMPTY) {
        ContentType.fromFilePath(
            fileNameByUri(
                context.contentResolver, uri
            )
        ).firstOrNull()?.toString() ?: ContentType.Application.OctetStream.toString()
    } else {
        ContentType.Application.OctetStream.toString()
    }
}