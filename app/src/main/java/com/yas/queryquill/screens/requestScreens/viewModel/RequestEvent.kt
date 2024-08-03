package com.yas.queryquill.screens.requestScreens.viewModel

import com.yas.domain.requestsDb.models.AddRequestModel

sealed interface RequestEvent {
    data class AddRequest(val model: AddRequestModel) : RequestEvent
    data class DeleteRequest(val id: Long) : RequestEvent
    data class SetRequest(val id: Long?) : RequestEvent
    data object SaveRequest : RequestEvent
}