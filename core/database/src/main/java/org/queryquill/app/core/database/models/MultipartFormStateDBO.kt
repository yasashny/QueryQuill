package org.queryquill.app.core.database.models

import android.net.Uri
import kotlinx.serialization.Serializable
import org.queryquill.app.core.database.utils.UriAsStringSerializer

@Serializable
internal sealed interface MultipartFormStateDBO {

    @Serializable
    data class Text(val keyValue: KeyValueDBO) : MultipartFormStateDBO

    @Serializable
    data class BinaryFile(
        val uri: @Serializable(UriAsStringSerializer::class) Uri,
        val title: String,
        val fileName: String
    ) : MultipartFormStateDBO
}

