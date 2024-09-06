package org.queryquill.app.data.requests.mappers

import org.queryquill.app.data.requests.models.ResponseDBO
import org.queryquill.app.data.requests.models.ResponseDTO

internal fun ResponseDTO.toDBO(id: Long): ResponseDBO {
    return ResponseDBO(
        id = id,
        status = status,
        fileName = fileName,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        headers = headers
    )
}