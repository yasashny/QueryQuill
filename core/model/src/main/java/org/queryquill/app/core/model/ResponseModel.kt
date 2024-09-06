package org.queryquill.app.core.model

data class ResponseModel(
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: ContentType,
    val headers: ImmutableList<KeyValue>
) {
    companion object {
        const val DEFAULT_FILE_NAME = "default.txt"

        fun default(): ResponseModel {
            return ResponseModel(
                "--",
                DEFAULT_FILE_NAME,
                "--",
                "--",
                ContentType.Text.PLAIN,
                ImmutableList(emptyList())
            )
        }
    }
}
