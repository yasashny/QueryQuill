/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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