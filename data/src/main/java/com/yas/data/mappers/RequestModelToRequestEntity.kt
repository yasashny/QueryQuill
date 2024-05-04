package com.yas.data.mappers

import com.yas.data.local.models.RequestEntity
import com.yas.domain.requestsDb.models.RequestModel

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