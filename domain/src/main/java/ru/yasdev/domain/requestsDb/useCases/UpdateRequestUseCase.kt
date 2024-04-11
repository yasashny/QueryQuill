package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.repositories.RequestsDbRepository

class UpdateRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(model: RequestModel) {
        repository.updateRequest(model)
    }

}