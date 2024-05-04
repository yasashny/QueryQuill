package com.yas.domain.sendRequest

import com.yas.domain.requestsDb.models.RequestModel

data class RequestResponseModel(
    val request: RequestModel,
    val response: ResponseModel
)