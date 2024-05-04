package com.yas.data.mappers

import com.yas.data.local.models.RequestEntity
import com.yas.domain.requestsDb.models.ImmutableList
import com.yas.domain.requestsDb.models.RequestModel

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