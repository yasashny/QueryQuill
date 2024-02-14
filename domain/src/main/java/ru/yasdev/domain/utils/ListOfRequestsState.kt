package ru.yasdev.domain.utils

import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

sealed interface ListOfRequestsState {
    object Loading: ListOfRequestsState
    data class ListOfRequests(val list: List<RequestsListItemModel>): ListOfRequestsState
}