package ru.yasdev.data.mappers

import ru.yasdev.data.requestsDb.models.RequestEntity
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.AuthState
import ru.yasdev.domain.requestsDb.models.BodyState
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue

fun AddRequestModel.toRequestEntity(): RequestEntity {
    return RequestEntity(
        label = label,
        bodyState = BodyState.NoBody,
        header = listOf(KeyValue.empty()),
        query = listOf(KeyValue.empty()),
        type = HttpType.GET,
        url = "",
        authState = AuthState.NoAuth
    )
}