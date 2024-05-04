package com.yas.data.mappers

import com.yas.data.local.models.ResponseEntity
import com.yas.domain.sendRequest.ResponseModel

fun ResponseModel.toResponseEntity(id: Int): ResponseEntity{
    return ResponseEntity(
        status = status,
        body = body,
        contentLength = contentLength,
        id = id,
        time = time
    )
}