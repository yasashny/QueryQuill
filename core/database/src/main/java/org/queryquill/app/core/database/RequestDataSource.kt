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