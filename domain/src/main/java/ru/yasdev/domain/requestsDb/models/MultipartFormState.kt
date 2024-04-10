package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import kotlinx.serialization.Serializable
@Serializable
sealed interface MultipartFormState {
    @Serializable
    data class Text(val keyValue: KeyValue): MultipartFormState

    @Serializable
    data class File(val uri: @Serializable(UriAsLongSerializer::class) Uri): MultipartFormState
}