package ru.yasdev.domain.lastRequest

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.utils.LastIdState

interface LastRequestRepository {

    fun getLastRequestId(): Flow<Int?>
    suspend fun saveLastRequestId(lastIdState: LastIdState)

}