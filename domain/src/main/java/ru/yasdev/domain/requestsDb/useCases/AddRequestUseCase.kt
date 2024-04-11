package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.repositories.RequestsDbRepository

class AddRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(model: AddRequestModel): RequestModel {
        return repository.addRequest(model)
    }
}