package ru.yasdev.domain.useCases

import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.AddRequestModel

class AddRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(model: AddRequestModel){
        repository.addRequest(model)
    }
}