package ru.yasdev.domain.requestsDb.useCases

import ru.yasdev.domain.requestsDb.repositories.LocalDbRepository

class DeleteRequestUseCase(private val repository: LocalDbRepository) {

    suspend fun execute(id: Int) {
        repository.deleteRequest(id)
    }
}