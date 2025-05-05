/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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