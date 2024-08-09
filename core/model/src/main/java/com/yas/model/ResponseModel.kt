package com.yas.model

data class ResponseModel(
    val status: String,
    val body: ImmutableByteArray,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?,
    val headers: ImmutableList<KeyValue>
) {
    companion object {
        fun default(): ResponseModel {
            return ResponseModel(
                "--",
                ImmutableByteArray(byteArrayOf()),
                "--",
                "--",
                null,
                null,
                ImmutableList(emptyList())
            )
        }
    }
}
