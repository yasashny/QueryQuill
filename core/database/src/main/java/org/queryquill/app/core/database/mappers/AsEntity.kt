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

package org.queryquill.app.core.database.mappers

import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.TextType
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.core.database.models.AuthStateDBO
import org.queryquill.app.core.database.models.BodyStateDBO
import org.queryquill.app.core.database.models.HttpTypeDBO
import org.queryquill.app.core.database.models.KeyValueDBO
import org.queryquill.app.core.database.models.MultipartFormStateDBO
import org.queryquill.app.core.database.models.RequestEntity
import org.queryquill.app.core.database.models.ResponseEntity
import org.queryquill.app.core.database.models.TextTypeDBO
import org.queryquill.app.core.database.models.TransactionEntity
import org.queryquill.app.core.model.ResponseModel

internal fun RequestModel.asEntity(): RequestEntity {
    return RequestEntity(
        id = id,
        bodyState = bodyState.asDBO(),
        header = header.map { it.asDBO() },
        query = query.map { it.asDBO() },
        authState = auth.asDBO(),
        type = type.asDBO(),
        url = url
    )
}

internal fun ResponseModel.asEntity(): ResponseEntity {
    return ResponseEntity(
        id = id,
        status = status,
        fileName = fileName,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        headers = headers.map { it.asDBO() }
    )
}

internal fun Transaction.asEntity(): TransactionEntity {
    return TransactionEntity(id = id, label = label)
}

private fun KeyValue.asDBO(): KeyValueDBO {
    return KeyValueDBO(key = key, value = value)
}

private fun MultipartFormState.asDBO(): MultipartFormStateDBO {
    return when (this) {
        is MultipartFormState.BinaryFile -> MultipartFormStateDBO.BinaryFile(
            uri = uri, fileName = fileName, title = title
        )

        is MultipartFormState.Text -> MultipartFormStateDBO.Text(keyValue = keyValue.asDBO())
    }
}

private fun BodyState.asDBO(): BodyStateDBO {
    return when (this) {
        is BodyState.BinaryFile -> BodyStateDBO.BinaryFile(uri = uri, fileName = fileName)
        is BodyState.FormUrlEncoded -> BodyStateDBO.FormUrlEncoded(list = list.map { it.asDBO() })
        is BodyState.MultipartForm -> BodyStateDBO.MultipartForm(multipart = multipart.map { it.asDBO() })
        BodyState.NoBody -> BodyStateDBO.NoBody
        is BodyState.Text -> BodyStateDBO.Text(
            textFileName = textFileName,
            textType = textType.asDBO()
        )
    }
}

private fun HttpType.asDBO(): HttpTypeDBO {
    return when (this) {
        HttpType.GET -> HttpTypeDBO.GET
        HttpType.POST -> HttpTypeDBO.POST
        HttpType.PUT -> HttpTypeDBO.PUT
        HttpType.PATCH -> HttpTypeDBO.PATCH
        HttpType.DELETE -> HttpTypeDBO.DELETE
        HttpType.OPTIONS -> HttpTypeDBO.OPTIONS
        HttpType.HEAD -> HttpTypeDBO.HEAD
    }
}

private fun TextType.asDBO(): TextTypeDBO {
    return when (this) {
        TextType.JSON -> TextTypeDBO.JSON
        TextType.XML -> TextTypeDBO.XML
        TextType.PLAIN -> TextTypeDBO.PLAIN
        TextType.OTHER -> TextTypeDBO.OTHER
    }
}

private fun AuthState.asDBO(): AuthStateDBO {
    return when (this) {
        is AuthState.Basic -> AuthStateDBO.Basic(userName = userName, password = password)
        AuthState.NoAuth -> AuthStateDBO.NoAuth
    }
}

