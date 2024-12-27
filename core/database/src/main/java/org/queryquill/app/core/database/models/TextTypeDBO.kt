package org.queryquill.app.core.database.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TextTypeDBO {
    @SerialName("JSON")
    JSON,

    @SerialName("XML")
    XML,

    @SerialName("PLAIN")
    PLAIN,

    @SerialName("OTHER")
    OTHER
}