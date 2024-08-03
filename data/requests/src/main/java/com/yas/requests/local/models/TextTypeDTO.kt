package com.yas.requests.local.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TextTypeDTO {
    @SerialName("JSON")
    JSON,

    @SerialName("XML")
    XML,

    @SerialName("PLAIN")
    PLAIN,

    @SerialName("OTHER")
    OTHER
}