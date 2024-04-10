package ru.yasdev.data.requestsDb

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import ru.yasdev.data.mappers.toRequestEntity
import ru.yasdev.data.mappers.toRequestModel
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

class RequestDbDataSource(context: Context) {

    private val db = Room.databaseBuilder(
        context, RequestDataBase::class.java, "request.db"
    ).build()


    fun getListOfRequests(): Flow<List<RequestsListItemModel>> {
        return db.dao.getListOfRequests()
    }

    suspend fun getRequest(id: Int): RequestModel {
        val requestEntity = db.dao.getRequest(id)
        return requestEntity.toRequestModel()
    }

    suspend fun addRequest(model: AddRequestModel): RequestModel {
        val id = db.dao.insertRequest(
            model.toRequestEntity()
        )
        return getRequest(id.toInt())

    }

    suspend fun updateRequest(model: RequestModel) {
        db.dao.insertRequest(
            model.toRequestEntity()
        )
    }

    suspend fun deleteRequest(id: Int) {
        db.dao.deleteRequest(id)
    }
}