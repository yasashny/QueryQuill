package com.yas.model

import android.net.Uri
import androidx.compose.runtime.Immutable


@Immutable
sealed interface BodyState : BasicState {

    override val name: String


    data class Text(val text: String, val textType: TextType) : BodyState {
        override val name: String
            get() = "Text"

        companion object {
            fun default(): Text {
                return Text("", TextType.JSON)
            }
        }
    }


    data class MultipartForm(val multipart: List<MultipartFormState>) : BodyState {
        override val name: String
            get() = "Multipart Form"

        companion object {
            fun default(): MultipartForm {
                return MultipartForm(listOf(MultipartFormState.Text(KeyValue.empty())))
            }
        }
    }


    data class FormUrlEncoded(val list: List<com.yas.model.KeyValue>) : BodyState {
        override val name: String
            get() = "Form Url Encoded"

        companion object {
            fun default(): FormUrlEncoded {
                return FormUrlEncoded(listOf(KeyValue.empty()))
            }
        }
    }


    data class BinaryFile(
        override val uri: Uri,
        override val fileName: String
    ) : BodyState, BasicBinaryFile() {
        override val name: String
            get() = "Binary File"

        companion object {
            fun default(): BinaryFile {
                return BinaryFile(Uri.EMPTY, "")
            }
        }
    }


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