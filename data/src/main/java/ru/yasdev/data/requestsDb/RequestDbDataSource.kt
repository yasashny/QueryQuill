package ru.yasdev.data.requestsDb

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.yasdev.data.requestsDb.models.Request
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

class RequestDbDataSource(context: Context) {

    private val db =
        Room.databaseBuilder(
            context,
            RequestDataBase::class.java,
            "request.db"
        ).build()


    fun getListOfRequests(): Flow<List<RequestsListItemModel>> {
        return db.dao.getListOfRequests()
    }

    fun getRequest(id: Int): Flow<RequestModel> {
        return flow{
            val model = db.dao.getRequest(id)
            emit(model)
        }
    }

    suspend fun addRequest(model: AddRequestModel): RequestModel {
        val id = db.dao.insertRequest(Request(label = model.label, test = "test"))
        val request = getRequest(id.toInt()).first()
        return request

    }

    suspend fun updateRequest(model: RequestModel) {
        db.dao.insertRequest(Request(id = model.id, label = model.label, test = model.test))
    }

    suspend fun deleteRequest(id: Int) {
        db.dao.deleteRequest(id)
    }
}