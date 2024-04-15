package ru.yasdev.data.mappers

import ru.yasdev.data.local.models.ResponseEntity
import ru.yasdev.domain.sendRequest.ResponseModel

fun ResponseEntity.toResponseModel(): ResponseModel {
    return ResponseModel(
        status = status,
        body = body,
        contentLength = contentLength,
        time = time
    )
}