package com.yas.requests.local

import com.yas.model.AddRequestModel
import com.yas.model.RequestModel
import com.yas.model.RequestsListItem
import com.yas.model.ResponseModel
import com.yas.requests.mappers.toDBO
import com.yas.requests.mappers.toDTO
import com.yas.requests.mappers.toModel
import kotlinx.coroutines.flow.Flow
import com.yas.requests.models.ResponseDBO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RequestsRepository internal constructor(private val requestsLocalDataSource: RequestsLocalDataSource, private val currentRequestLocalDataSource: CurrentRequestLocalDataSource){

    fun getListOfRequests(): Flow<List<RequestsListItem>> {
        return requestsLocalDataSource.getListOfRequests().map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    fun getCurrentRequestOrNull(): Flow<RequestModel?> {
        return currentRequestLocalDataSource.getId().map { value: Long? ->
            if(value != null){
                requestsLocalDataSource.getRequest(id = value).toModel()
            }
            else{
                null
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentResponseOrNull(): Flow<ResponseModel?> {
        return currentRequestLocalDataSource.getId().flatMapLatest { value: Long? ->
            if (value != null){
                requestsLocalDataSource.getResponse(id = value).map { responseDBO: ResponseDBO ->
                    responseDBO.toModel()
                }
            }
            else{
                flowOf(null)
            }
        }
    }

    suspend fun changeCurrentRequestId(requestId: Long?) {
        currentRequestLocalDataSource.saveId(requestId)
    }



    suspend fun addRequest(model: AddRequestModel) {
        val id = requestsLocalDataSource.add(model.toDTO())
        currentRequestLocalDataSource.saveId(id)
    }

    suspend fun updateRequest(model: RequestModel) {
        requestsLocalDataSource.updateRequest(model.toDBO())
    }


    suspend fun deleteRequest(id: Long) {
        requestsLocalDataSource.delete(id)
        currentRequestLocalDataSource.saveId(null)
    }
}