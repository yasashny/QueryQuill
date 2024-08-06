package com.yas.model

import android.net.Uri


sealed interface MultipartFormState : BasicState {
    override val name: String


    data class Text(val keyValue: KeyValue) : MultipartFormState {
        override val name: String
            get() = "TEXT"

        companion object {
            fun default(): Text {
                return Text(KeyValue.empty())
            }
        }
    }


    data class BinaryFile(override val uri: Uri, val title: String,
                          override val fileName: String) :
        MultipartFormState, BasicBinaryFile() {
        override val name: String
            get() = "FILE"

        companion object {
            fun default(): BinaryFile {
                return BinaryFile(Uri.EMPTY, "", "")
            }
        }
    }

    override fun isDefault(): Boolean {
        return when (this) {
            is BinaryFile -> this == BinaryFile.default()
            is Text -> this == Text.default()
        }
    }
}