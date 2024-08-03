package com.yas.requests.local.storage

import android.content.Context
import androidx.room.Room
import com.yas.requests.local.db.RequestsDataBase
import com.yas.requests.local.mappers.toRequestDBO
import com.yas.requests.local.models.AddRequestModelDTO
import com.yas.requests.local.models.RequestDBO
import com.yas.requests.local.models.RequestsListItemDTO
import com.yas.requests.local.models.ResponseDBO
import kotlinx.coroutines.flow.Flow

internal class RequestsStorage(context: Context) {

    private val db = Room.databaseBuilder(
        context, RequestsDataBase::class.java, "request.db"
    ).build()


    fun getListOfRequests(): Flow<List<RequestsListItemDTO>> {
        return db.requestDao.getListOfRequests()
    }

    suspend fun getRequest(id: Long): RequestDBO {
        return db.requestDao.getRequest(id)

    }

    fun getResponse(id: Long): Flow<ResponseDBO> {
        return db.responseDao.getResponse(id)
    }

    suspend fun add(model: AddRequestModelDTO): Long {
        val id = db.requestDao.insertRequest(
            model.toRequestDBO()
        )
        db.responseDao.insertResponse(
            ResponseDBO(
                id = id,
                status = "--",
                body = byteArrayOf(),
                contentLength = "--",
                time = "--",
                contentType = null,
                contentSubtype = null,
                headers = emptyList()
            )
        )
        return id
    }

    suspend fun updateRequest(model: RequestDBO) {
        db.requestDao.insertRequest(
            model
        )
    }

    suspend fun updateResponse(model: ResponseDBO) {
        db.responseDao.insertResponse(model)
    }

    suspend fun delete(id: Long) {
        db.requestDao.deleteRequest(id)
        db.responseDao.deleteResponse(id)
    }
}