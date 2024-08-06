package com.yas.queryquill.screens.requestScreens.viewModel

import com.yas.model.NewRequestModel

sealed interface RequestEvent {
    data class DeleteRequest(val id: Long) : RequestEvent
    data class SetRequest(val id: Long?) : RequestEvent
    data object SaveRequest : RequestEvent
}