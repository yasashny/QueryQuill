package com.yas.data.mappers

import com.yas.data.local.models.RequestEntity
import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState

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