package ru.yasdev.data.LastRequest

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.utils.LastIdState

class LastRequestRepositoryImpl(private val dataSource: LastRequestDataSource): LastRequestRepository {
    override fun getLastRequestId(): Flow<LastIdState> {
        return dataSource.getId()
    }

    override suspend fun saveLastRequestId(lastIdState: LastIdState) {
        dataSource.saveId(lastIdState)
    }


}