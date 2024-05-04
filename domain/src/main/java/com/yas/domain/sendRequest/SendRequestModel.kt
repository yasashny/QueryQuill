package com.yas.domain.sendRequest

import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState

data class SendRequestModel(
    val bodyState: BodyState,
    val query: List<KeyValue>,
    val headers: List<KeyValue>,
    val auth: AuthState,
    val url: String,
    val type: HttpType
)
