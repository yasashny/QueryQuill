package com.yas.queryquill.navigationDrawer

import com.yas.model.RequestsListItem

sealed interface ListOfRequestsState {
    data object Loading : ListOfRequestsState
    data class ListOfRequests(val list: List<RequestsListItem>) : ListOfRequestsState
}