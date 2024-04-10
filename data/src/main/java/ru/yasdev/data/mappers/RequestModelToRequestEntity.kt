package ru.yasdev.data.mappers

import ru.yasdev.data.requestsDb.models.RequestEntity
import ru.yasdev.domain.requestsDb.models.RequestModel

fun RequestModel.toRequestEntity(): RequestEntity {
    return RequestEntity(
        id = id,
        label = label,
        body = body,
        header = header.list,
        query = query.list,
        type = type,
        url = url,
        auth = auth
    )
}