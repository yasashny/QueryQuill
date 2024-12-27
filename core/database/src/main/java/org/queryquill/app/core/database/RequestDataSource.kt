package org.queryquill.app.core.database

import org.queryquill.app.core.database.dao.RequestDao
import org.queryquill.app.core.database.mappers.asEntity
import org.queryquill.app.core.database.mappers.asExternalModel
import org.queryquill.app.core.database.models.AuthStateDBO
import org.queryquill.app.core.database.models.BodyStateDBO
import org.queryquill.app.core.database.models.HttpTypeDBO
import org.queryquill.app.core.database.models.KeyValueDBO
import org.queryquill.app.core.database.models.RequestEntity
import org.queryquill.app.core.model.RequestModel

class RequestDataSource internal constructor(private val dao: RequestDao) {

    suspend fun create(id: Long) {
        dao.insertRequest(
            RequestEntity(
                id = id,
                bodyState = BodyStateDBO.NoBody,
                header = listOf(KeyValueDBO.empty()),
                query = listOf(KeyValueDBO.empty()),
                type = HttpTypeDBO.GET,
                url = "",
                authState = AuthStateDBO.NoAuth
            )
        )
    }

    suspend fun read(id: Long): RequestModel? {
        return dao.getRequest(id)?.asExternalModel()
    }

    suspend fun update(model: RequestModel) {
        dao.insertRequest(
            model.asEntity()
        )
    }

    suspend fun delete(id: Long) {
        dao.deleteRequest(id)
    }
}