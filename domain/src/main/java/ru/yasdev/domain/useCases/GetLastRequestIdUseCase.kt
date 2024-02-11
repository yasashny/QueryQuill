package ru.yasdev.domain.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.utils.LastIdState

class GetLastRequestIdUseCase(private val repository: LastRequestRepository) {

    fun execute(): Flow<LastIdState> {
        return repository.getLastRequestId()
    }

}