package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.repositories.RequestsDbRepository

class GetRequestUseCase(private val requestsDbRepository: RequestsDbRepository) {

    suspend fun execute(id: Int): RequestModel {
        return requestsDbRepository.getRequest(id)
    }

}