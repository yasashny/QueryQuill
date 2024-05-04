package com.yas.domain.requestsDb.useCases

import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.repositories.LocalDbRepository
import com.yas.domain.sendRequest.RequestResponseModel

class AddRequestUseCase(private val repository: LocalDbRepository) {

    suspend fun execute(model: AddRequestModel): RequestResponseModel {
        return repository.addRequest(model)
    }
}