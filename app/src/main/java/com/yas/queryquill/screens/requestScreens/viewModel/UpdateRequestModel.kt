package com.yas.queryquill.screens.requestScreens.viewModel

import com.yas.model.HttpType
import com.yas.model.KeyValue
import com.yas.model.AuthState
import com.yas.model.BodyState

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