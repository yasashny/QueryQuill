package org.queryquill.app.data.requests.mappers

import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.TextType
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.data.requests.models.AuthStateDTO
import org.queryquill.app.data.requests.models.BodyStateDTO
import org.queryquill.app.data.requests.models.HttpTypeDTO
import org.queryquill.app.data.requests.models.KeyValueDTO
import org.queryquill.app.data.requests.models.MultipartFormStateDTO
import org.queryquill.app.data.requests.models.NewTransactionModelDTO
import org.queryquill.app.data.requests.models.RequestDBO
import org.queryquill.app.data.requests.models.RequestDTO
import org.queryquill.app.data.requests.models.TextTypeDTO
import org.queryquill.app.data.requests.models.TransactionDBO

internal fun RequestModel.toDBO(): RequestDBO {
    return RequestDBO(
        id = id,
        bodyState = bodyState.toDTO(),
        header = header.list.map { it.toDTO() },
        query = query.list.map { it.toDTO() },
        authState = auth.toDTO(),
        type = type.toDTO(),
        url = url
    )
}

private fun BodyState.toDTO(): BodyStateDTO {
    return when (this) {
        is BodyState.BinaryFile -> BodyStateDTO.BinaryFile(uri = uri.uri, fileName = fileName)
        is BodyState.FormUrlEncoded -> BodyStateDTO.FormUrlEncoded(list = list.list.map { it.toDTO() })
        is BodyState.MultipartForm -> BodyStateDTO.MultipartForm(multipart = multipart.list.map { it.toDTO() })
        BodyState.NoBody -> BodyStateDTO.NoBody
        is BodyState.Text -> BodyStateDTO.Text(
            textFileName = textFileName,
            textType = textType.toDTO()
        )
    }
}

private fun KeyValue.toDTO(): KeyValueDTO {
    return KeyValueDTO(key = key, value = value)
}

private fun MultipartFormState.toDTO(): MultipartFormStateDTO {
    return when (this) {
        is MultipartFormState.BinaryFile -> MultipartFormStateDTO.BinaryFile(
            uri = uri.uri, fileName = fileName, title = title
        )

        is MultipartFormState.Text -> MultipartFormStateDTO.Text(keyValue = keyValue.toDTO())
    }
}

private fun HttpType.toDTO(): HttpTypeDTO {
    return when (this) {
        HttpType.GET -> HttpTypeDTO.GET
        HttpType.POST -> HttpTypeDTO.POST
        HttpType.PUT -> HttpTypeDTO.PUT
        HttpType.PATCH -> HttpTypeDTO.PATCH
        HttpType.DELETE -> HttpTypeDTO.DELETE
        HttpType.OPTIONS -> HttpTypeDTO.OPTIONS
        HttpType.HEAD -> HttpTypeDTO.HEAD
    }
}

private fun TextType.toDTO(): TextTypeDTO {
    return when (this) {
        TextType.JSON -> TextTypeDTO.JSON
        TextType.XML -> TextTypeDTO.XML
        TextType.PLAIN -> TextTypeDTO.PLAIN
        TextType.OTHER -> TextTypeDTO.OTHER
    }
}

private fun AuthState.toDTO(): AuthStateDTO {
    return when (this) {
        is AuthState.Basic -> AuthStateDTO.Basic(userName = userName, password = password)
        AuthState.NoAuth -> AuthStateDTO.NoAuth
    }
}

internal fun NewTransactionModel.toDTO(): NewTransactionModelDTO {
    return NewTransactionModelDTO(label = label)
}

internal fun RequestModel.toDTO(): RequestDTO {
    return RequestDTO(
        id = id,
        bodyState = bodyState.toDTO(),
        header = header.list.map { it.toDTO() },
        authState = auth.toDTO(),
        query = query.list.map { it.toDTO() },
        type = type.toDTO(),
        url = url
    )
}

internal fun Transaction.toDBO(): TransactionDBO {
    return TransactionDBO(id = id, label = label)
}

