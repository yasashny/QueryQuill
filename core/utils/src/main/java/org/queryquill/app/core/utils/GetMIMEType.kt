package org.queryquill.app.core.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import io.ktor.http.ContentType
import io.ktor.http.fromFilePath

typealias FileNameResolver = (resolver: ContentResolver, uri: Uri) -> String

/**
 * Determines and returns the MIME type of a given URI.
 *
 * @param context The context required to access the content resolver.
 * @param uri The URI of the file for which the MIME type is to be determined.
 * @return A string representing the MIME type of the file. If the MIME type cannot be determined,
 * it defaults to "application/octet-stream".
 */
fun getMIMEType(
    context: Context, uri: Uri, fileNameResolver: FileNameResolver = ::fileNameByUri
): String {
    if (uri == Uri.EMPTY) return ContentType.Application.OctetStream.toString()

    val name = fileNameResolver(context.contentResolver, uri)
    return ContentType.fromFilePath(name).firstOrNull()?.toString()
        ?: ContentType.Application.OctetStream.toString()
}