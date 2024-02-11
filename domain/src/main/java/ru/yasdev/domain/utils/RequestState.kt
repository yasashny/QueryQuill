package ru.yasdev.domain.utils

import ru.yasdev.domain.requestsDb.models.RequestModel

sealed interface RequestState {
    object Loading: RequestState
    object NullRequest: RequestState
    data class Request(val requestModel: RequestModel): RequestState


}