package ru.yasdev.data.mappers

import ru.yasdev.data.local.models.ResponseEntity
import ru.yasdev.domain.sendRequest.ResponseModel

fun ResponseModel.toResponseEntity(id: Int): ResponseEntity{
    return ResponseEntity(
        status = status,
        body = body,
        contentLength = contentLength,
        id = id
    )
}