package org.queryquill.app.data.requests.mappers

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
import org.queryquill.app.data.requests.models.AuthStateDTO
import org.queryquill.app.data.requests.models.BodyStateDTO
import org.queryquill.app.data.requests.models.HttpTypeDTO
import org.queryquill.app.data.requests.models.KeyValueDTO
import org.queryquill.app.data.requests.models.MultipartFormStateDTO
import org.queryquill.app.data.requests.models.RequestDBO
import org.queryquill.app.data.requests.models.ResponseDBO
import org.queryquill.app.data.requests.models.TextTypeDTO
import org.queryquill.app.data.requests.models.TransactionDBO

internal fun RequestDBO.toModel(): RequestModel {
    return RequestModel(
        id = id,
        bodyState = bodyState.toModel(),
        header = ImmutableList(list = header.map { it.toModel() }),
        query = ImmutableList(list = query.map { it.toModel() }),
        auth = authState.toModel(),
        type = type.toModel(),
        url = url
    )
}

private fun BodyStateDTO.toModel(): BodyState {
    return when (this) {

        is BodyStateDTO.BinaryFile -> BodyState.BinaryFile(
            uri = ImmutableUri(uri), fileName = fileName
        )

        is BodyStateDTO.FormUrlEncoded -> BodyState.FormUrlEncoded(list = ImmutableList(list.map { it.toModel() }))
        is BodyStateDTO.MultipartForm -> BodyState.MultipartForm(multipart = ImmutableList(multipart.map { it.toModel() }))
        BodyStateDTO.NoBody -> BodyState.NoBody
        is BodyStateDTO.Text -> BodyState.Text(
            textFileName = textFileName, textType = textType.toModel()
        )
    }
}

private fun KeyValueDTO.toModel(): KeyValue {
    return KeyValue(key = key, value = value)
}

private fun MultipartFormStateDTO.toModel(): MultipartFormState {
    return when (this) {
        is MultipartFormStateDTO.BinaryFile -> MultipartFormState.BinaryFile(
            uri = ImmutableUri(uri), fileName = fileName, title = title
        )

        is MultipartFormStateDTO.Text -> MultipartFormState.Text(keyValue = keyValue.toModel())
    }
}

private fun HttpTypeDTO.toModel(): HttpType {
    return when (this) {
        HttpTypeDTO.GET -> HttpType.GET
        HttpTypeDTO.POST -> HttpType.POST
        HttpTypeDTO.PUT -> HttpType.PUT
        HttpTypeDTO.PATCH -> HttpType.PATCH
        HttpTypeDTO.DELETE -> HttpType.DELETE
        HttpTypeDTO.OPTIONS -> HttpType.OPTIONS
        HttpTypeDTO.HEAD -> HttpType.HEAD
    }
}

private fun TextTypeDTO.toModel(): TextType {
    return when (this) {
        TextTypeDTO.JSON -> TextType.JSON
        TextTypeDTO.XML -> TextType.XML
        TextTypeDTO.PLAIN -> TextType.PLAIN
        TextTypeDTO.OTHER -> TextType.OTHER
    }
}

private fun AuthStateDTO.toModel(): AuthState {
    return when (this) {
        is AuthStateDTO.Basic -> AuthState.Basic(userName = userName, password = password)
        AuthStateDTO.NoAuth -> AuthState.NoAuth
    }
}

internal fun ResponseDBO.toModel(): ResponseModel {
    return ResponseModel(
        status = status,
        fileName = fileName,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        headers = ImmutableList(headers.map { it.toModel() })
    )
}

internal fun TransactionDBO.toModel(): Transaction {
    return Transaction(id = id, label = label)
}


