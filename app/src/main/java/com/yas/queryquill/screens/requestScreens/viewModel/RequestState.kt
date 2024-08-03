package com.yas.queryquill.screens.requestScreens.viewModel

import com.yas.domain.requestsDb.models.RequestModel

sealed interface RequestState {
    data object Loading : RequestState
    data object NewRequest : RequestState
    data class Request(val request: RequestModel) : RequestState
}