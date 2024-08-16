package com.yas.requests.mappers

import com.yas.requests.models.ResponseDBO
import com.yas.requests.models.ResponseDTO

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