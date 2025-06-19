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

package org.queryquill.app.feature.request

import android.net.Uri
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState

internal sealed interface UpdateRequest {
    enum class UpdateType {
        DELETE, UPDATE
    }

    sealed interface Body : UpdateRequest {
        data class TextType(val textType: org.queryquill.app.core.model.TextType) : Body
        data class ChangeType(
            val newState: BodyState.Type,
            val force: Boolean = false,
            val onDirtyBody: () -> Unit = {}
        ) : Body

        data class FormUrlEncoded(val updateType: UpdateType, val item: KeyValue) : Body
        data class MultipartForm(val updateType: UpdateType, val item: MultipartFormState) : Body
        sealed interface BinaryFile : Body {
            data class File(
                val uri: Uri,
                val fileName: String,
                val contentType: String,
                val showChangeTypeDialog: () -> Unit = {}
            ) : BinaryFile

            data class ChangeContentTypeInHeaders(
                val contentType: String
            ) : BinaryFile
        }

    }

    sealed interface Auth : UpdateRequest {
        data class ChangeType(
            val authType: AuthState.Type,
            val force: Boolean = false,
            val onDirtyAuth: () -> Unit = {}
        ) : Auth

        data class Basic(val basicState: AuthState.Basic) : Auth
    }

    data class Url(val url: String) : UpdateRequest
    data class Headers(val updateType: UpdateType, val item: KeyValue) : UpdateRequest
    data class Query(val updateType: UpdateType, val item: KeyValue) : UpdateRequest
    data class Type(val httpType: HttpType) : UpdateRequest
}