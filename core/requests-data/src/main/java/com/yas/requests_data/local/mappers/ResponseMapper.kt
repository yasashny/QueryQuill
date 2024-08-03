package com.yas.requests_data.local.mappers

import com.yas.requests_data.local.models.ResponseDBO
import com.yas.requests_data.local.models.ResponseDTO

internal fun ResponseDBO.toDTO(): ResponseDTO{
    return ResponseDTO(
        status = status,
        body = body,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        contentSubtype = contentSubtype,
        headers = headers
    )
}

internal fun ResponseDTO.toDBO(id: Long): ResponseDBO{
    return ResponseDBO(
        id = id,
        status = status,
        body = body,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        contentSubtype = contentSubtype,
        headers = headers
    )
}