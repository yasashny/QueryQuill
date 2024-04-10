package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import ru.yasdev.domain.requestsDb.serializers.UriAsStringSerializer

@Serializable
@Immutable
sealed interface BodyState {
    @Serializable
    data class Text(val text: String) : BodyState

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormState>) : BodyState

    @Serializable
    data class FormUrlEncoded(val list: List<KeyValue>) : BodyState

    @Serializable
    data class BinaryFile(val uri: @Serializable(UriAsStringSerializer::class) Uri) : BodyState

    @Serializable
    data object NoBody : BodyState
}