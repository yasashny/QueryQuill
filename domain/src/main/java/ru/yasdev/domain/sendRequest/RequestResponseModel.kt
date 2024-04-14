package ru.yasdev.domain.sendRequest

import ru.yasdev.domain.requestsDb.models.RequestModel

data class RequestResponseModel(
    val request: RequestModel,
    val response: ResponseModel
)