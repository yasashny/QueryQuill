package com.yas.requests_data.local.mappers

import com.yas.requests_data.local.models.RequestDBO
import com.yas.requests_data.local.models.RequestDTO

internal fun RequestDBO.toDTO(): RequestDTO{
    return RequestDTO(
        id = id,
        label = label,
        bodyState = bodyState,
        header = header,
        query = query,
        authState = authState,
        type = type,
        url = url
    )
}

internal fun RequestDTO.toDBO(): RequestDBO{
    return RequestDBO(
        id = id,
        label = label,
        bodyState = bodyState,
        header = header,
        query = query,
        authState = authState,
        type = type,
        url = url
    )
}