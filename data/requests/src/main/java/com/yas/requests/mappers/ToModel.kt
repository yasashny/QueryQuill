package com.yas.requests.mappers

import com.yas.model.AuthState
import com.yas.model.BodyState
import com.yas.model.HttpType
import com.yas.model.ImmutableList
import com.yas.model.ImmutableUri
import com.yas.model.KeyValue
import com.yas.model.MultipartFormState
import com.yas.model.RequestModel
import com.yas.model.ResponseModel
import com.yas.model.TextType
import com.yas.model.Transaction
import com.yas.requests.models.AuthStateDTO
import com.yas.requests.models.BodyStateDTO
import com.yas.requests.models.HttpTypeDTO
import com.yas.requests.models.KeyValueDTO
import com.yas.requests.models.MultipartFormStateDTO
import com.yas.requests.models.RequestDBO
import com.yas.requests.models.ResponseDBO
import com.yas.requests.models.TextTypeDTO
import com.yas.requests.models.TransactionDBO

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
        contentSubtype = contentSubtype,
        headers = ImmutableList(headers.map { it.toModel() })
    )
}

internal fun TransactionDBO.toModel(): Transaction {
    return Transaction(id = id, label = label)
}
