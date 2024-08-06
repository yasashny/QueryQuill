package com.yas.requests.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TextTypeDTO {
    @SerialName("JSON")
    JSON,

    @SerialName("XML")
    XML,

    @SerialName("PLAIN")
    PLAIN,

    @SerialName("OTHER")
    OTHER
}