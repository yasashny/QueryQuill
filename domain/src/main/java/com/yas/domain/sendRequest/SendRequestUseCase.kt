package com.yas.domain.sendRequest

import com.yas.domain.requestsDb.models.RequestModel

class SendRequestUseCase(private val repository: SendRequestRepository) {

    suspend fun execute(requestModel: RequestModel): ResponseModel{
        return repository.sendRequest(requestModel.toSendRequestModel())
    }
}