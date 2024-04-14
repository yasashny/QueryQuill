package ru.yasdev.data.mappers

import ru.yasdev.data.local.models.RequestEntity
import ru.yasdev.domain.requestsDb.models.ImmutableList
import ru.yasdev.domain.requestsDb.models.RequestModel

fun RequestEntity.toRequestModel(): RequestModel {
    return RequestModel(
        id = id,
        label = label,
        bodyState = bodyState,
        header = ImmutableList(header),
        query = ImmutableList(query),
        type = type,
        url = url,
        auth = authState
    )
}