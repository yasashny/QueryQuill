package ru.yasdev.domain.useCases

import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.RequestModel

class UpdateRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(model: RequestModel){
        repository.updateRequest(model)
    }

}