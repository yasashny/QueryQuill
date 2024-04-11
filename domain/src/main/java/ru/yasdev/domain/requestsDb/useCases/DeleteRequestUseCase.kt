package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.repositories.RequestsDbRepository

class DeleteRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(id: Int) {
        repository.deleteRequest(id)
    }
}