package ru.yasdev.data.requestsDb

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import ru.yasdev.data.requestsDb.models.DataRequestModel
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.ImmutableList
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel
import ru.yasdev.domain.requestsDb.models.ListItem

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

    suspend fun getRequest(id: Int): RequestModel {
        val dataModel = db.dao.getRequest(id)
        return RequestModel(
            id = dataModel.id,
            label = dataModel.label,
            body = dataModel.body,
            header = ImmutableList( dataModel.header),
            query = ImmutableList(dataModel.query),
            type = dataModel.type,
            url = dataModel.url
        )
    }

    suspend fun addRequest(model: AddRequestModel): RequestModel {
        val id = db.dao.insertRequest(
            DataRequestModel(
                label = model.label, body = Body.Text(""), header = listOf(
                    ListItem("", "")
                ), query = emptyList(), type = HttpType.GET, url = ""
            )
        )
        return getRequest(id.toInt())

    }

    suspend fun updateRequest(model: RequestModel) {
        db.dao.insertRequest(
            DataRequestModel(
                id = model.id,
                label = model.label,
                body = model.body,
                header = model.header.list,
                query = model.query.list,
                type = model.type,
                url = model.url
            )
        )
    }

    suspend fun deleteRequest(id: Int) {
        db.dao.deleteRequest(id)
    }
}