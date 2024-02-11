package ru.yasdev.queryquill.activity

import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel

sealed interface RequestEvent {
    data class AddRequest(val model: AddRequestModel): RequestEvent
    data class DeleteRequest(val id: Int): RequestEvent
    data class UpdateRequest(val requestModel: RequestModel): RequestEvent
    object SaveLastId: RequestEvent
}