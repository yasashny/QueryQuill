package com.yas.data.mappers

import com.yas.data.local.models.ResponseEntity
import com.yas.domain.sendRequest.ResponseModel

fun ResponseEntity.toResponseModel(): ResponseModel {
    return ResponseModel(
        status = status,
        body = body,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        contentSubtype = contentSubtype
    )
}