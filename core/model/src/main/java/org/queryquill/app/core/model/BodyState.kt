package org.queryquill.app.core.model

import android.net.Uri

sealed interface BodyState : BasicState {

    override val name: String


    data class Text(val textFileName: String, val textType: TextType) : BodyState {
        override val name: String
            get() = "Text"

        companion object {
            fun default(id: Long): Text {
                return Text(
                    "${id}_request.txt", TextType.JSON
                )
            }
        }
    }


    data class MultipartForm(val multipart: ImmutableList<MultipartFormState>) : BodyState {
        override val name: String
            get() = "Multipart Form"

        companion object {
            fun default(): MultipartForm {
                return MultipartForm(
                    ImmutableList(
                        listOf(
                            MultipartFormState.Text(
                                KeyValue.empty()
                            )
                        )
                    )
                )
            }
        }
    }


    data class FormUrlEncoded(val list: ImmutableList<KeyValue>) : BodyState {
        override val name: String
            get() = "Form Url Encoded"

        companion object {
            fun default(): FormUrlEncoded {
                return FormUrlEncoded(
                    ImmutableList(
                        listOf(KeyValue.empty())
                    )
                )
            }
        }
    }


    data class BinaryFile(
        override val uri: ImmutableUri, override val fileName: String
    ) : BodyState, BasicBinaryFile() {
        override val name: String
            get() = "Binary File"

        companion object {
            fun default(): BinaryFile {
                return BinaryFile(
                    ImmutableUri(Uri.EMPTY), ""
                )
            }
        }
    }


    data object NoBody : BodyState {
        override val name: String
            get() = "No Body"
    }
}