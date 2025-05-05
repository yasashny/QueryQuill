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

package org.queryquill.app.feature.request.body

import org.queryquill.app.core.model.BodyState

internal fun BodyState.toEnum(): EnumBodyState {
    return when (this) {
        is BodyState.BinaryFile -> EnumBodyState.BinaryFile
        is BodyState.FormUrlEncoded -> EnumBodyState.FormUrlEncoded
        is BodyState.MultipartForm -> EnumBodyState.MultipartForm
        BodyState.NoBody -> EnumBodyState.NoBody
        is BodyState.Text -> EnumBodyState.Text
    }
}

internal fun EnumBodyState.toBodyState(id: Long): BodyState {
    return when (this) {
        EnumBodyState.NoBody -> BodyState.NoBody
        EnumBodyState.Text -> BodyState.Text.default(id)
        EnumBodyState.FormUrlEncoded -> BodyState.FormUrlEncoded.default()
        EnumBodyState.MultipartForm -> BodyState.MultipartForm.default()
        EnumBodyState.BinaryFile -> BodyState.BinaryFile.default()
    }
}