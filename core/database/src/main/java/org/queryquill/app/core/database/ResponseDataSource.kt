package org.queryquill.app.core.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.queryquill.app.core.database.dao.ResponseDao
import org.queryquill.app.core.database.mappers.asEntity
import org.queryquill.app.core.database.mappers.asExternalModel
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.database.models.ResponseEntity
import org.queryquill.app.core.model.ResponseModel

class ResponseDataSource internal constructor(private val dao: ResponseDao) {

    suspend fun create(id: Long) {
        dao.insertResponse(
            ResponseEntity(
                id = id,
                status = "--",
                fileName = "default.txt",
                contentLength = "--",
                time = "--",
                contentType = ContentType.Text.PLAIN,
                headers = emptyList()
            )
        )
    }

    fun read(id: Long): Flow<ResponseModel?> {
        return dao.getResponse(id).map { it?.asExternalModel() }
    }

    suspend fun update(model: ResponseModel) {
        dao.insertResponse(model.asEntity())
    }

    suspend fun delete(id: Long) {
        dao.deleteResponse(id)
    }
}