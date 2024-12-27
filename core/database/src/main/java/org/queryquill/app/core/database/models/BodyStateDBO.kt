package org.queryquill.app.core.database.models

import android.net.Uri
import kotlinx.serialization.Serializable
import org.queryquill.app.core.database.utils.UriAsStringSerializer

@Serializable
internal sealed interface BodyStateDBO {


    @Serializable
    data class Text(val textFileName: String, val textType: TextTypeDBO) : BodyStateDBO

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormStateDBO>) : BodyStateDBO

    @Serializable
    data class FormUrlEncoded(val list: List<KeyValueDBO>) : BodyStateDBO

    @Serializable
    data class BinaryFile(
        val uri: @Serializable(UriAsStringSerializer::class) Uri,
        val fileName: String
    ) : BodyStateDBO

    @Serializable
    data object NoBody : BodyStateDBO
}