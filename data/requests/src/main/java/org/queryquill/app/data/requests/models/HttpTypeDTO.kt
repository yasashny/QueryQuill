package org.queryquill.app.data.requests.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class HttpTypeDTO {
    @SerialName("GET")
    GET,

    @SerialName("POST")
    POST,

    @SerialName("PUT")
    PUT,

    @SerialName("PATCH")
    PATCH,

    @SerialName("DELETE")
    DELETE,

    @SerialName("OPTIONS")
    OPTIONS,

    @SerialName("HEAD")
    HEAD
}