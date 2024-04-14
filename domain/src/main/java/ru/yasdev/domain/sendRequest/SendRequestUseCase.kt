package ru.yasdev.domain.sendRequest

import ru.yasdev.domain.requestsDb.models.RequestModel

class SendRequestUseCase(private val repository: SendRequestRepository) {

    suspend fun execute(requestModel: RequestModel): ResponseModel{
        return repository.sendRequest(requestModel.toSendRequestModel())
    }
}