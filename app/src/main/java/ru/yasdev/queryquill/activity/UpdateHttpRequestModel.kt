package ru.yasdev.queryquill.activity

import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue

sealed interface UpdateHttpRequestModel {
    data class Body(val body: ru.yasdev.domain.requestsDb.models.Body) : UpdateHttpRequestModel
    data class Header(val header: List<KeyValue>) : UpdateHttpRequestModel
    data class Query(val query: List<KeyValue>) : UpdateHttpRequestModel
    data class Type(val type: HttpType) : UpdateHttpRequestModel
    data class Url(val url: String) : UpdateHttpRequestModel
    data class Label(val label: String): UpdateHttpRequestModel
    data class Auth(val auth: ru.yasdev.domain.requestsDb.models.Auth): UpdateHttpRequestModel
}