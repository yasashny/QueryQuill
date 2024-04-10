package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import kotlinx.serialization.Serializable
import ru.yasdev.domain.requestsDb.serializers.UriAsStringSerializer

@Serializable
sealed interface MultipartFormState {
    val name: String
    @Serializable
    data class Text(val keyValue: KeyValue) : MultipartFormState{
        override val name: String
            get() = "TEXT"
    }

    @Serializable
    data class File(val uri: @Serializable(UriAsStringSerializer::class) Uri) : MultipartFormState{
        override val name: String
            get() = "FILE"
    }
}