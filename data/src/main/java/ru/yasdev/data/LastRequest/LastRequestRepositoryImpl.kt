package ru.yasdev.data.LastRequest

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.utils.RequestState

class LastRequestRepositoryImpl(private val dataSource: LastRequestDataSource): LastRequestRepository {
    override fun getLastRequestId(): Flow<Int?> {
        return dataSource.getId()
    }

    override suspend fun saveLastRequestId(id: Int?) {
        dataSource.saveId(id)
    }


}