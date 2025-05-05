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

sealed interface AuthState : BasicState {
    override val name: String


    data object NoAuth : AuthState {
        override val name: String
            get() = "No Auth"
    }


    data class Basic(val userName: String, val password: String) : AuthState {
        override val name: String
            get() = "Basic"

        companion object {
            fun default(): Basic {
                return Basic("", "")
            }
        }
    }

    fun isDefault(): Boolean {
        return when (this) {
            is Basic -> this == Basic.default()
            NoAuth -> true
        }
    }
}