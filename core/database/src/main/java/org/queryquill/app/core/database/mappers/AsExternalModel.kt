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
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.ImmutableUri
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
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

internal fun RequestEntity.asExternalModel(): RequestModel {
    return RequestModel(
        id = id,
        bodyState = bodyState.asExternalModel(),
        header = ImmutableList(list = header.map { it.asExternalModel() }),
        query = ImmutableList(list = query.map { it.asExternalModel() }),
        auth = authState.asExternalModel(),
        type = type.asExternalModel(),
        url = url
    )
}

internal fun ResponseEntity.asExternalModel(): ResponseModel {
    return ResponseModel(
        id = id,
        status = status,
        fileName = fileName,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        headers = ImmutableList(headers.map { it.asExternalModel() })
    )
}

internal fun TransactionEntity.asExternalModel(): Transaction {
    return Transaction(id = id, label = label)
}

private fun BodyStateDBO.asExternalModel(): BodyState {
    return when (this) {

        is BodyStateDBO.BinaryFile -> BodyState.BinaryFile(
            uri = ImmutableUri(uri), fileName = fileName
        )

        is BodyStateDBO.FormUrlEncoded -> BodyState.FormUrlEncoded(list = ImmutableList(list.map { it.asExternalModel() }))
        is BodyStateDBO.MultipartForm -> BodyState.MultipartForm(multipart = ImmutableList(multipart.map { it.asExternalModel() }))
        BodyStateDBO.NoBody -> BodyState.NoBody
        is BodyStateDBO.Text -> BodyState.Text(
            textFileName = textFileName, textType = textType.asExternalModel()
        )
    }
}

private fun KeyValueDBO.asExternalModel(): KeyValue {
    return KeyValue(key = key, value = value)
}

private fun MultipartFormStateDBO.asExternalModel(): MultipartFormState {
    return when (this) {
        is MultipartFormStateDBO.BinaryFile -> MultipartFormState.BinaryFile(
            uri = ImmutableUri(uri), fileName = fileName, title = title
        )

        is MultipartFormStateDBO.Text -> MultipartFormState.Text(keyValue = keyValue.asExternalModel())
    }
}

private fun HttpTypeDBO.asExternalModel(): HttpType {
    return when (this) {
        HttpTypeDBO.GET -> HttpType.GET
        HttpTypeDBO.POST -> HttpType.POST
        HttpTypeDBO.PUT -> HttpType.PUT
        HttpTypeDBO.PATCH -> HttpType.PATCH
        HttpTypeDBO.DELETE -> HttpType.DELETE
        HttpTypeDBO.OPTIONS -> HttpType.OPTIONS
        HttpTypeDBO.HEAD -> HttpType.HEAD
    }
}

private fun TextTypeDBO.asExternalModel(): TextType {
    return when (this) {
        TextTypeDBO.JSON -> TextType.JSON
        TextTypeDBO.XML -> TextType.XML
        TextTypeDBO.PLAIN -> TextType.PLAIN
        TextTypeDBO.OTHER -> TextType.OTHER
    }
}

private fun AuthStateDBO.asExternalModel(): AuthState {
    return when (this) {
        is AuthStateDBO.Basic -> AuthState.Basic(userName = userName, password = password)
        AuthStateDBO.NoAuth -> AuthState.NoAuth
    }
}


