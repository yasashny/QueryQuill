package com.yas.queryquill.navigationDrawer

import com.yas.domain.requestsDb.models.RequestsListItemModel

sealed interface ListOfRequestsState {
    data object Loading : ListOfRequestsState
    data class ListOfRequests(val list: List<RequestsListItemModel>) : ListOfRequestsState
}