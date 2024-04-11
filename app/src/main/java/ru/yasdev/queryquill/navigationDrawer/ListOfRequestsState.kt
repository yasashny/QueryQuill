package ru.yasdev.queryquill.navigationDrawer

import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

sealed interface ListOfRequestsState {
    data object Loading : ListOfRequestsState
    data class ListOfRequests(val list: List<RequestsListItemModel>) : ListOfRequestsState
}