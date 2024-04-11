package ru.yasdev.queryquill.screens.requestScreens.viewModel

import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.states.AuthState
import ru.yasdev.domain.requestsDb.states.BodyState

sealed interface UpdateHttpRequestModel {
    data class Body(val bodyState: BodyState) :
        UpdateHttpRequestModel
    data class Header(val header: List<KeyValue>) : UpdateHttpRequestModel
    data class Query(val query: List<KeyValue>) : UpdateHttpRequestModel
    data class Type(val type: HttpType) : UpdateHttpRequestModel
    data class Url(val url: String) : UpdateHttpRequestModel
    data class Label(val label: String) : UpdateHttpRequestModel
    data class Auth(val authState: AuthState) :
        UpdateHttpRequestModel
}