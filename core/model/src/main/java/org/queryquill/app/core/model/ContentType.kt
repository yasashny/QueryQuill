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

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
sealed interface ContentType {
    val fileType: String

    @Serializable
    data object Image {
        @Serializable
        data object JPEG : ContentType {
            override val fileType: String
                get() = "jpeg"
        }

        @Serializable
        data object PNG : ContentType {
            override val fileType: String
                get() = "png"
        }

        @Serializable
        data object WEBP : ContentType {
            override val fileType: String
                get() = "webp"
        }

        @Serializable
        data object BMP : ContentType {
            override val fileType: String
                get() = "bmp"
        }

        @Serializable
        data object HEIF : ContentType {
            override val fileType: String
                get() = "heif"
        }

        @Serializable
        data object HEIC : ContentType {
            override val fileType: String
                get() = "heic"
        }
    }

    @Serializable
    data object Text {
        @Serializable
        data object HTML : ContentType {
            override val fileType: String
                get() = "html"
        }

        @Serializable
        data object PLAIN : ContentType {
            override val fileType: String
                get() = "txt"
        }

        @Serializable
        data object XML : ContentType {
            override val fileType: String
                get() = "xml"
        }
    }

    @Serializable
    data object Application {
        @Serializable
        data object JSON : ContentType {
            override val fileType: String
                get() = "json"
        }
    }
}