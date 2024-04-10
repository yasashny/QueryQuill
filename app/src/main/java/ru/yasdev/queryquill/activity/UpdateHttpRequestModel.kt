package ru.yasdev.queryquill.activity

import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue

sealed interface UpdateHttpRequestModel {
    data class Body(val bodyState: ru.yasdev.domain.requestsDb.models.BodyState) : UpdateHttpRequestModel
    data class Header(val header: List<KeyValue>) : UpdateHttpRequestModel
    data class Query(val query: List<KeyValue>) : UpdateHttpRequestModel
    data class Type(val type: HttpType) : UpdateHttpRequestModel
    data class Url(val url: String) : UpdateHttpRequestModel
    data class Label(val label: String): UpdateHttpRequestModel
    data class Auth(val authState: ru.yasdev.domain.requestsDb.models.AuthState): UpdateHttpRequestModel
}