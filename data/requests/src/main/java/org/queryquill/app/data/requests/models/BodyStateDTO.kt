package org.queryquill.app.data.requests.models

import android.net.Uri
import kotlinx.serialization.Serializable
import org.queryquill.app.data.requests.serializers.UriAsStringSerializer

@Serializable
internal sealed interface BodyStateDTO {


    @Serializable
    data class Text(val textFileName: String, val textType: TextTypeDTO) : BodyStateDTO

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormStateDTO>) : BodyStateDTO

    @Serializable
    data class FormUrlEncoded(val list: List<KeyValueDTO>) : BodyStateDTO

    @Serializable
    data class BinaryFile(
        val uri: @Serializable(UriAsStringSerializer::class) Uri,
        val fileName: String
    ) : BodyStateDTO

    @Serializable
    data object NoBody : BodyStateDTO
}