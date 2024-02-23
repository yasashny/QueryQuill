package ru.yasdev.domain.useCases

import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.RequestModel

class GetRequestUseCase(private val requestsDbRepository: RequestsDbRepository) {

    suspend fun execute(id: Int): RequestModel {
        return requestsDbRepository.getRequest(id)
    }

}