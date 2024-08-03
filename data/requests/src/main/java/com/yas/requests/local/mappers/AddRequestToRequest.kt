package com.yas.requests.local.mappers

import com.yas.requests.local.models.AddRequestModelDTO
import com.yas.requests.local.models.AuthStateDTO
import com.yas.requests.local.models.BodyStateDTO
import com.yas.requests.local.models.HttpTypeDTO
import com.yas.requests.local.models.KeyValueDTO
import com.yas.requests.local.models.RequestDBO

internal fun AddRequestModelDTO.toRequestDBO(): RequestDBO {
    return RequestDBO(
        label = label,
        bodyState = BodyStateDTO.NoBody,
        header = listOf(KeyValueDTO.empty()),
        query = listOf(KeyValueDTO.empty()),
        type = HttpTypeDTO.GET,
        url = "",
        authState = AuthStateDTO.NoAuth
    )
}