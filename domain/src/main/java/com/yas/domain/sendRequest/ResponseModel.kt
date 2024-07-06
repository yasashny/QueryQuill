package com.yas.domain.sendRequest

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel(
    val status: String,
    val body: ByteArray,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?
) {
    companion object {
        fun default(): ResponseModel {
            return ResponseModel("--", byteArrayOf(), "--", "--", null, null)
        }

        fun errorType(message: ByteArray): ResponseModel {
            return ResponseModel("ERROR", message, "--", "--", null, null)
        }
    }
}
