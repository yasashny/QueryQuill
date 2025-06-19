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

sealed interface BodyState {

    enum class Type(override val labelRes: Int) : HasLabelRes {
        NoBody(R.string.no_body), Text(R.string.text), FormUrlEncoded(R.string.form_url_encoded), MultipartForm(
            R.string.multipart_form
        ),
        BinaryFile(R.string.binary_file)
    }

    val type: Type

    data class Text(val textFileName: String = "", val textType: TextType = TextType.JSON) :
        BodyState {
        override val type: Type
            get() = Type.Text
    }

    data class MultipartForm(
        val multipart: List<MultipartFormState> = listOf(
            MultipartFormState.Text(
                KeyValue()
            )
        )
    ) : BodyState {
        override val type: Type
            get() = Type.MultipartForm

    }

    data class FormUrlEncoded(val list: List<KeyValue> = listOf(KeyValue())) : BodyState {
        override val type: Type
            get() = Type.FormUrlEncoded


    }

    data class BinaryFile(
        override val uri: Uri = Uri.EMPTY, override val fileName: String = ""
    ) : BodyState, FileInfo {
        override val type: Type
            get() = Type.BinaryFile


    }

    data object NoBody : BodyState {
        override val type: Type
            get() = Type.NoBody
    }
}