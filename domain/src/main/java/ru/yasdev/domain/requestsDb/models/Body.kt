@file:Suppress("SERIALIZER_TYPE_INCOMPATIBLE")

package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import androidx.compose.runtime.Immutable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.json.JSONArray

@Serializable
@Immutable
sealed interface Body {
    @Serializable
    data class Text(val text: String) : Body

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormState>) :
        Body

    @Serializable
    data class FormUrlEncoded(val list: List<ListItem>) : Body

    @Serializable
    data class BinaryFile(val uri: @Serializable(UriAsLongSerializer::class) Uri) : Body

    @Serializable
    data object NoBody : Body

}

object UriAsLongSerializer : KSerializer<Uri> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Uri) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): Uri {
        val resValue = decoder.decodeString().replace("\"", "")
        return Uri.parse(resValue).normalizeScheme()
    }
}