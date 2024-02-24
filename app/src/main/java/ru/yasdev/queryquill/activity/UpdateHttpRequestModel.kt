package ru.yasdev.queryquill.activity

import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.ListItem

sealed interface UpdateHttpRequestModel {
    data class Body(val body: ru.yasdev.domain.requestsDb.models.Body) : UpdateHttpRequestModel
    data class Header(val header: List<ListItem>) : UpdateHttpRequestModel
    data class Query(val query: List<ListItem>) : UpdateHttpRequestModel
    data class Type(val type: HttpType) : UpdateHttpRequestModel
    data class Url(val url: String) : UpdateHttpRequestModel
    data class Label(val label: String): UpdateHttpRequestModel
}