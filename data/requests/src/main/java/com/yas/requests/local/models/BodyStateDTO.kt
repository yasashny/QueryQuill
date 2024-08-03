package com.yas.requests.local.models

import android.net.Uri
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
sealed interface BodyStateDTO {


    @Serializable
    data class Text(val text: String, val textType: TextTypeDTO) : BodyStateDTO

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormStateDTO>) : BodyStateDTO

    @Serializable
    data class FormUrlEncoded(val list: List<KeyValueDTO>) : BodyStateDTO

    @Serializable
    data class BinaryFile(
        val uri: @Serializable(com.yas.requests.serializers.UriAsStringSerializer::class) Uri,
        val fileName: String
    ) : BodyStateDTO

    @Serializable
    data object NoBody : BodyStateDTO
}