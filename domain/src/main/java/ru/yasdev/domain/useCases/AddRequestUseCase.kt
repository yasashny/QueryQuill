package ru.yasdev.domain.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel

class AddRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(model: AddRequestModel): Flow<RequestModel> {
        return repository.addRequest(model)
    }
}