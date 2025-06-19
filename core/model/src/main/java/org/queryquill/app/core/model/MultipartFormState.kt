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

@file:OptIn(ExperimentalUuidApi::class)

package org.queryquill.app.core.model

import android.net.Uri
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed interface MultipartFormState {

    val id: String

    data class Text(
        val keyValue: KeyValue = KeyValue(), override val id: String = Uuid.random().toString()
    ) : MultipartFormState

    data class BinaryFile(
        override val uri: Uri = Uri.EMPTY,
        val title: String = "",
        override val fileName: String = "",
        override val id: String = Uuid.random().toString()
    ) : MultipartFormState, FileInfo
}