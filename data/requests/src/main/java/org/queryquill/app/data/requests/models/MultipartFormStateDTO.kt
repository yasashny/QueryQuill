package org.queryquill.app.data.requests.models

import android.net.Uri
import kotlinx.serialization.Serializable
import org.queryquill.app.data.requests.serializers.UriAsStringSerializer

@Serializable
internal sealed interface MultipartFormStateDTO {

    @Serializable
    data class Text(val keyValue: KeyValueDTO) : MultipartFormStateDTO

    @Serializable
    data class BinaryFile(
        val uri: @Serializable(UriAsStringSerializer::class) Uri,
        val title: String,
        val fileName: String
    ) : MultipartFormStateDTO
}

