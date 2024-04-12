package ru.yasdev.domain.sendRequest

import ru.yasdev.domain.requestsDb.models.RequestModel

class SendRequestUseCase(private val repository: SendRequestRepository) {

    suspend fun execute(requestModel: RequestModel){
        repository.sendRequest(requestModel.toSendRequestModel())
    }
}