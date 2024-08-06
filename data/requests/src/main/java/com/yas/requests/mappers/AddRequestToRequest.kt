package com.yas.requests.mappers

import com.yas.requests.models.AddRequestModelDTO
import com.yas.requests.models.AuthStateDTO
import com.yas.requests.models.BodyStateDTO
import com.yas.requests.models.HttpTypeDTO
import com.yas.requests.models.KeyValueDTO
import com.yas.requests.models.RequestDBO

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