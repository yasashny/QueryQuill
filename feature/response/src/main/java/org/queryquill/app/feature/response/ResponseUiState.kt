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

package org.queryquill.app.feature.response

import android.net.Uri
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.ResponseModel

data class ResponseUiState(
    val model: ResponseModel = ResponseModel(
        -1, "--", DEFAULT_FILE_NAME, "--", "--", ContentType.Text.PLAIN, emptyList()
    ), val fileLength: Long = 0, val fileUri: Uri = Uri.EMPTY
) {
    companion object {
        const val DEFAULT_FILE_NAME = "default.txt"
    }
}
