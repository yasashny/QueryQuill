package com.yas.model

sealed interface UpdateRequestModel {
    data class Body(val bodyState: BodyState) :
        UpdateRequestModel

    data class Header(val header: List<KeyValue>) : UpdateRequestModel
    data class Query(val query: List<KeyValue>) : UpdateRequestModel
    data class Type(val type: HttpType) : UpdateRequestModel
    data class Url(val url: String) : UpdateRequestModel
    data class Auth(val authState: AuthState) :
        UpdateRequestModel
}