package com.yas.domain.sendRequest

import com.yas.domain.requestsDb.models.KeyValue

data class ResponseModel(
    val status: String,
    val body: ByteArray,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?,
    val headers: List<KeyValue>
) {
    companion object {
        fun default(): ResponseModel {
            return ResponseModel("--", byteArrayOf(), "--", "--", null, null, emptyList())
        }
    }
}
