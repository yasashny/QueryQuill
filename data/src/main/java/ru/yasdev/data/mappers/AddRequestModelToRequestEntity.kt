package ru.yasdev.data.mappers

import ru.yasdev.data.requestsDb.models.RequestEntity
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.states.AuthState
import ru.yasdev.domain.requestsDb.states.BodyState

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