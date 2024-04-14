package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.repositories.LocalDbRepository
import ru.yasdev.domain.sendRequest.RequestResponseModel

class GetRequestUseCase(private val localDbRepository: LocalDbRepository) {

    suspend fun execute(id: Int): RequestResponseModel {
        return localDbRepository.getRequest(id)
    }

}