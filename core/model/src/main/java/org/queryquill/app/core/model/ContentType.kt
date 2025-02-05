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