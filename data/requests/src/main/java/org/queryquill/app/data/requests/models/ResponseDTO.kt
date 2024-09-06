package org.queryquill.app.data.requests.models

import kotlinx.serialization.Serializable
import org.queryquill.app.core.model.ContentType

@Serializable
internal data class ResponseDTO(
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: ContentType,
    val headers: List<KeyValueDTO>
) {
    companion object {

        fun errorType(message: String): ResponseDTO {
            return ResponseDTO("ERROR", message, "--", "--", ContentType.Text.PLAIN, emptyList())
        }
    }
}