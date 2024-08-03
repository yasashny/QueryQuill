package com.yas.queryquill.screens.requestScreens.viewModel

import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState

sealed interface UpdateRequestModel {
    data class Body(val bodyState: BodyState) :
        UpdateRequestModel
    data class Header(val header: List<KeyValue>) : UpdateRequestModel
    data class Query(val query: List<KeyValue>) : UpdateRequestModel
    data class Type(val type: HttpType) : UpdateRequestModel
    data class Url(val url: String) : UpdateRequestModel
    data class Label(val label: String) : UpdateRequestModel
    data class Auth(val authState: AuthState) :
        UpdateRequestModel
}