package ru.yasdev.data.LastRequest

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.LastRequestRepository

class LastRequestRepositoryImpl(val dataSource: LastRequestDataSource): LastRequestRepository {
    override fun getLastRequestId(): Flow<Int?> {
        return dataSource.getId()
    }

    override suspend fun saveLastRequestId(id: Int) {
        dataSource.saveId(id)
    }


}