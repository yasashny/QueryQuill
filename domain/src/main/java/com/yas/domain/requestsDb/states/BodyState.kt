package com.yas.domain.requestsDb.states

import android.net.Uri
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.serializers.UriAsStringSerializer

@Serializable
@Immutable
sealed interface BodyState : BasicState {

    override val name: String

    @Serializable
    data class Text(val text: String) : BodyState {
        override val name: String
            get() = "Text"

        companion object {
            fun default(): Text {
                return Text("")
            }
        }
    }

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormState>) : BodyState {
        override val name: String
            get() = "Multipart Form"

        companion object {
            fun default(): MultipartForm {
                return MultipartForm(listOf(MultipartFormState.Text(KeyValue.empty())))
            }
        }
    }

    @Serializable
    data class FormUrlEncoded(val list: List<KeyValue>) : BodyState {
        override val name: String
            get() = "Form Url Encoded"

        companion object {
            fun default(): FormUrlEncoded {
                return FormUrlEncoded(listOf(KeyValue.empty()))
            }
        }
    }

    @Serializable
    data class BinaryFile(override val uri: @Serializable(UriAsStringSerializer::class) Uri,
                          override val fileName: String) :
        BodyState, BasicBinaryFile() {
        override val name: String
            get() = "Binary File"

        companion object {
            fun default(): BinaryFile {
                return BinaryFile(Uri.EMPTY, "")
            }
        }
    }

    @Serializable
    data object NoBody : BodyState {
        override val name: String
            get() = "No Body"
    }

    override fun isDefault(): Boolean {
        return when (this) {
            is Text -> this == Text.default()
            is MultipartForm -> this == MultipartForm.default()
            is FormUrlEncoded -> this == FormUrlEncoded.default()
            is BinaryFile -> this == BinaryFile.default()
            is NoBody -> true
        }
    }
}