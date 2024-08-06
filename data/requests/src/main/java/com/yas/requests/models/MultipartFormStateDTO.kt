package com.yas.requests.models

import android.net.Uri
import com.yas.requests.serializers.UriAsStringSerializer
import kotlinx.serialization.Serializable

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

