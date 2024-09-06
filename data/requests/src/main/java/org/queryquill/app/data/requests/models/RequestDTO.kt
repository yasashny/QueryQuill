package org.queryquill.app.data.requests.models

internal data class RequestDTO(
    val id: Long,
    val bodyState: BodyStateDTO,
    val header: List<KeyValueDTO>,
    val query: List<KeyValueDTO>,
    val authState: AuthStateDTO,
    val type: HttpTypeDTO,
    val url: String
)
