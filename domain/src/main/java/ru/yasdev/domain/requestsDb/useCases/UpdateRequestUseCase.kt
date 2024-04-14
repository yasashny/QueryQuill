package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.repositories.LocalDbRepository
import ru.yasdev.domain.sendRequest.RequestResponseModel

class UpdateRequestUseCase(private val repository: LocalDbRepository) {

    suspend fun execute(model: RequestResponseModel) {
        repository.updateRequest(model)
    }

}