package org.queryquill.app.core.network.utils

import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.ResponseModel

internal fun createErrorResponse(message: String, id: Long): ResponseModel {
    return ResponseModel(
        id = id,
        status = "ERROR",
        fileName = message,
        contentLength = "--",
        time = "--",
        contentType = ContentType.Text.PLAIN,
        headers = ImmutableList(emptyList())
    )
}