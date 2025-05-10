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

package org.queryquill.app.feature.request_code_editor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.queryquill.app.core.model.CodeEditorState
import java.io.File

internal suspend fun saveFile(file: File, state: CodeEditorState) {
    withContext(Dispatchers.IO){
        file.writeText("")
        var start = 0
        val end = state.content.length
        while (start < end) {
            file.appendBytes(
                state.content.substring(
                    start, if (start + 10000 > end) end else start + 10000
                ).toByteArray()
            )
            start += 10000
        }
    }
}