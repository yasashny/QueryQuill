package ru.yasdev.domain.lastRequest.useCases

import ru.yasdev.domain.lastRequest.repositories.LastRequestRepository

class SaveLastRequestIdUseCase(private val repository: LastRequestRepository) {
    suspend fun execute(id: Int?) {
        repository.saveLastRequestId(id)
    }

}