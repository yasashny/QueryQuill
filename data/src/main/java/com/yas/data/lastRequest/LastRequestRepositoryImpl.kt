package com.yas.data.lastRequest

import kotlinx.coroutines.flow.Flow
import com.yas.domain.lastRequest.repositories.LastRequestRepository

class LastRequestRepositoryImpl(private val dataSource: LastRequestDataSource) :
    LastRequestRepository {
    override fun getLastRequestId(): Flow<Int?> {
        return dataSource.getId()
    }

    override suspend fun saveLastRequestId(id: Int?) {
        dataSource.saveId(id)
    }

}