package com.yas.queryquill.screens.requestScreens.viewModel

import com.yas.domain.sendRequest.ResponseModel

sealed interface ResponseState {
    data object Loading : ResponseState
    data class Response(val model: ResponseModel) : ResponseState

}