package ru.yasdev.data.mappers

import ru.yasdev.data.local.models.RequestEntity
import ru.yasdev.domain.requestsDb.models.RequestModel

fun RequestModel.toRequestEntity(): RequestEntity {
    return RequestEntity(
        id = id,
        label = label,
        bodyState = bodyState,
        header = header.list,
        query = query.list,
        type = type,
        url = url,
        authState = auth
    )
}