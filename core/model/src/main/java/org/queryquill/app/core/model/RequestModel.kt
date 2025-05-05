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


data class RequestModel(
    val id: Long,
    val bodyState: BodyState,
    val header: ImmutableList<KeyValue>,
    val query: ImmutableList<KeyValue>,
    val auth: AuthState,
    val type: HttpType,
    val url: String
) {
    companion object {
        fun default(): RequestModel {
            return RequestModel(
                id = -1,
                bodyState = BodyState.NoBody,
                header = ImmutableList(emptyList()),
                query = ImmutableList(emptyList()),
                type = HttpType.GET,
                url = "",
                auth = AuthState.NoAuth
            )
        }
    }

}


