package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.repositories.LocalDbRepository
import ru.yasdev.domain.sendRequest.RequestResponseModel

class AddRequestUseCase(private val repository: LocalDbRepository) {

    suspend fun execute(model: AddRequestModel): RequestResponseModel {
        return repository.addRequest(model)
    }
}