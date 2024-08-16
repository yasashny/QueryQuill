package com.yas.model


sealed interface ContentType {
    data object Image {
        data object JPEG : ContentType
        data object PNG : ContentType
        data object WEBP : ContentType
    }

    data object Text {
        data object HTML : ContentType
        data object PLAIN : ContentType
        data object XML : ContentType
    }

    data object Application {
        data object JSON : ContentType
    }
}