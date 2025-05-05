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
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.auth.EnumAuthState
import org.queryquill.app.feature.request.body.EnumBodyState

internal sealed interface UpdateRequestModel {
    data object Body {
        data class UpdateTextType(val textType: TextType) : UpdateRequestModel
        data class ChangeType(val newState: EnumBodyState) : UpdateRequestModel
        data class FormUrlEncoded(val list: List<KeyValue>) : UpdateRequestModel
        data class MultipartForm(val list: List<MultipartFormState>) : UpdateRequestModel
        data class BinaryFile(
            val uri: Uri,
            val fileName: String,
            val isChangeContentType: Boolean,
            val contentType: String
        ) : UpdateRequestModel
    }

    data class Header(val list: List<KeyValue>) : UpdateRequestModel
    data class Query(val list: List<KeyValue>) : UpdateRequestModel
    data class Type(val type: HttpType) : UpdateRequestModel
    data class Url(val url: String) : UpdateRequestModel
    data object Auth {
        data class ChangeType(val authState: EnumAuthState) : UpdateRequestModel
        data class Basic(val basicState: AuthState.Basic) : UpdateRequestModel
    }
}