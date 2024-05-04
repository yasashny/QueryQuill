package com.yas.domain.requestsDb.useCases

import com.yas.domain.requestsDb.repositories.LocalDbRepository

class DeleteRequestUseCase(private val repository: LocalDbRepository) {

    suspend fun execute(id: Int) {
        repository.deleteRequest(id)
    }
}