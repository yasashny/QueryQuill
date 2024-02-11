package ru.yasdev.domain.useCases

import ru.yasdev.domain.requestsDb.RequestsDbRepository

class DeleteRequestUseCase(private val repository: RequestsDbRepository) {

    suspend fun execute(id: Int){
        repository.deleteRequest(id)
    }
}