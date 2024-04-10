package ru.yasdev.domain.requestsDb.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class HttpType {
    @SerialName("GET") GET,
    @SerialName("POST") POST,
    @SerialName("PUT") PUT,
    @SerialName("PATCH") PATCH,
    @SerialName("DELETE") DELETE,
    @SerialName("OPTIONS") OPTIONS,
    @SerialName("HEAD") HEAD
}