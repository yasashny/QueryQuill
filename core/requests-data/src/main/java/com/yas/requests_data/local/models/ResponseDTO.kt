package com.yas.requests_data.local.models

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    val status: String,
    val body: ByteArray,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?,
    val headers: List<KeyValueDTO>
) {
    companion object {

        fun errorType(message: ByteArray): ResponseDTO {
            return ResponseDTO("ERROR", message, "--", "--", null, null, emptyList())
        }
    }
}