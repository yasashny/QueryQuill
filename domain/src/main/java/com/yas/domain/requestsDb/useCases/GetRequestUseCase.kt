package com.yas.domain.requestsDb.useCases

import com.yas.domain.requestsDb.repositories.LocalDbRepository
import com.yas.domain.sendRequest.RequestResponseModel

class GetRequestUseCase(private val localDbRepository: LocalDbRepository) {

    suspend fun execute(id: Int): RequestResponseModel {
        return localDbRepository.getRequest(id)
    }

}