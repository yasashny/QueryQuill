package com.yas.domain.requestsDb.useCases

import com.yas.domain.requestsDb.repositories.LocalDbRepository
import com.yas.domain.sendRequest.RequestResponseModel

class UpdateRequestUseCase(private val repository: LocalDbRepository) {

    suspend fun execute(model: RequestResponseModel) {
        repository.updateRequest(model)
    }

}