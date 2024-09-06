package org.queryquill.app.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
sealed interface ContentType {
    @Serializable
    data object Image {
        @Serializable
        data object JPEG : ContentType

        @Serializable
        data object PNG : ContentType

        @Serializable
        data object WEBP : ContentType

        @Serializable
        data object BMP : ContentType

        @Serializable
        data object HEIF : ContentType

        @Serializable
        data object HEIC : ContentType
    }

    @Serializable
    data object Text {
        @Serializable
        data object HTML : ContentType

        @Serializable
        data object PLAIN : ContentType

        @Serializable
        data object XML : ContentType
    }

    @Serializable
    data object Application {
        @Serializable
        data object JSON : ContentType
    }
}