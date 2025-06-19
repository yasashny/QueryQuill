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

data class ResponseModel(
    val id: Long,
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: ContentType,
    val headers: List<KeyValue>
) {
    companion object {
        const val DEFAULT_FILE_NAME = "default.txt"

        fun default(): ResponseModel {
            return ResponseModel(
                -1,
                "--",
                DEFAULT_FILE_NAME,
                "--",
                "--",
                ContentType.Text.PLAIN,
                emptyList()
            )
        }
    }
}
