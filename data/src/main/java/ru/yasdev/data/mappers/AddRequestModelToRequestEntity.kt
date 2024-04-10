package ru.yasdev.data.mappers

import ru.yasdev.data.requestsDb.models.RequestEntity
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.Auth
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue

fun AddRequestModel.toRequestEntity(): RequestEntity {
    return RequestEntity(
        label = label,
        body = Body.NoBody,
        header = listOf(KeyValue.empty()),
        query = listOf(KeyValue.empty()),
        type = HttpType.GET,
        url = "",
        auth = Auth.NoAuth
    )
}