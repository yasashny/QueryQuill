package ru.yasdev.domain.sendRequest

import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.states.AuthState
import ru.yasdev.domain.requestsDb.states.BodyState

data class SendRequestModel(
    val bodyState: BodyState,
    val query: List<KeyValue>,
    val headers: List<KeyValue>,
    val auth: AuthState,
    val url: String,
    val type: HttpType
)
