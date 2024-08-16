package com.yas.model

data class ResponseModel(
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: ContentType,
    val headers: ImmutableList<KeyValue>
) {
    companion object {
        fun default(): ResponseModel {
            return ResponseModel(
                "--",
                "default.txt",
                "--",
                "--",
                ContentType.Text.PLAIN,
                ImmutableList(emptyList())
            )
        }
    }
}
