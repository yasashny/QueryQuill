package com.yas.requests.models

import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseDTO(
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?,
    val headers: List<KeyValueDTO>
) {
    companion object {

        fun errorType(message: String): ResponseDTO {
            return ResponseDTO("ERROR", message, "--", "--", null, null, emptyList())
        }
    }
}