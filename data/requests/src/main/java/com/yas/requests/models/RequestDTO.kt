package com.yas.requests.models

internal data class RequestDTO(
    val id: Long,
    val label: String,
    val bodyState: BodyStateDTO,
    val header: List<KeyValueDTO>,
    val query: List<KeyValueDTO>,
    val authState: AuthStateDTO,
    val type: HttpTypeDTO,
    val url: String
)
