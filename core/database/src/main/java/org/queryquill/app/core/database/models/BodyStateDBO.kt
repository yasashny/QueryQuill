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

package org.queryquill.app.core.database.models

import android.net.Uri
import kotlinx.serialization.Serializable
import org.queryquill.app.core.database.utils.UriAsStringSerializer

@Serializable
internal sealed interface BodyStateDBO {


    @Serializable
    data class Text(val textFileName: String, val textType: TextTypeDBO) : BodyStateDBO

    @Serializable
    data class MultipartForm(val multipart: List<MultipartFormStateDBO>) : BodyStateDBO

    @Serializable
    data class FormUrlEncoded(val list: List<KeyValueDBO>) : BodyStateDBO

    @Serializable
    data class BinaryFile(
        val uri: @Serializable(UriAsStringSerializer::class) Uri,
        val fileName: String
    ) : BodyStateDBO

    @Serializable
    data object NoBody : BodyStateDBO
}