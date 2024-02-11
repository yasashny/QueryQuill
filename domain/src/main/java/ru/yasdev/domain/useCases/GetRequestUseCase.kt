package ru.yasdev.domain.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.RequestModel

class GetRequestUseCase(private val requestsDbRepository: RequestsDbRepository) {

    fun execute(id: Int): Flow<RequestModel> {
        return requestsDbRepository.getRequest(id)
    }

}