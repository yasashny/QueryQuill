package com.yas.queryquill.mappers

import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.requestsDb.models.RequestsListItem
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState
import com.yas.domain.requestsDb.states.MultipartFormState
import com.yas.domain.requestsDb.states.TextType
import com.yas.domain.sendRequest.ResponseModel
import com.yas.requests.local.models.AddRequestModelDTO
import com.yas.requests.local.models.AuthStateDTO
import com.yas.requests.local.models.BodyStateDTO
import com.yas.requests.local.models.HttpTypeDTO
import com.yas.requests.local.models.KeyValueDTO
import com.yas.requests.local.models.MultipartFormStateDTO
import com.yas.requests.local.models.RequestDTO
import com.yas.requests.local.models.RequestsListItemDTO
import com.yas.requests.local.models.ResponseDTO
import com.yas.requests.local.models.TextTypeDTO

fun RequestModel.toDTO() : RequestDTO{
    return RequestDTO(
        id = id,
        label = label,
        bodyState = bodyState.toDTO(),
        header = header.list.map { it.toDTO() },
        query = query.list.map { it.toDTO() },
        authState = auth.toDTO(),
        type = type.toDTO(),
        url = url
    )
}

private fun BodyState.toDTO(): BodyStateDTO{
    return when(this){
        is BodyState.BinaryFile -> BodyStateDTO.BinaryFile(uri = uri, fileName = fileName)
        is BodyState.FormUrlEncoded -> BodyStateDTO.FormUrlEncoded(list = list.map { it.toDTO() })
        is BodyState.MultipartForm -> BodyStateDTO.MultipartForm(multipart = multipart.map { it.toDTO() })
        BodyState.NoBody -> BodyStateDTO.NoBody
        is BodyState.Text -> BodyStateDTO.Text(text = text, textType = textType.toDTO())
    }
}

private fun KeyValue.toDTO(): KeyValueDTO{
    return KeyValueDTO(key = key, value = value)
}

private fun MultipartFormState.toDTO(): MultipartFormStateDTO{
    return when(this){
        is MultipartFormState.BinaryFile -> MultipartFormStateDTO.BinaryFile(uri = uri, fileName = fileName, title = title)
        is MultipartFormState.Text -> MultipartFormStateDTO.Text(keyValue = keyValue.toDTO())
    }
}

private fun HttpType.toDTO() : HttpTypeDTO{
    return when(this){
        HttpType.GET -> HttpTypeDTO.GET
        HttpType.POST -> HttpTypeDTO.POST
        HttpType.PUT -> HttpTypeDTO.PUT
        HttpType.PATCH -> HttpTypeDTO.PATCH
        HttpType.DELETE -> HttpTypeDTO.DELETE
        HttpType.OPTIONS -> HttpTypeDTO.OPTIONS
        HttpType.HEAD -> HttpTypeDTO.HEAD
    }
}

private fun TextType.toDTO() : TextTypeDTO{
    return when(this){
        TextType.JSON -> TextTypeDTO.JSON
        TextType.XML -> TextTypeDTO.XML
        TextType.PLAIN -> TextTypeDTO.PLAIN
        TextType.OTHER -> TextTypeDTO.OTHER
    }
}

private fun AuthState.toDTO(): AuthStateDTO{
    return when(this){
        is AuthState.Basic -> AuthStateDTO.Basic(userName = userName, password = password)
        AuthState.NoAuth -> AuthStateDTO.NoAuth
    }
}



fun ResponseModel.toDTO() : ResponseDTO {
    return ResponseDTO(
        status = status,
        body = body,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        contentSubtype = contentSubtype,
        headers = headers.map { it.toDTO() }
    )
}

fun RequestsListItem.toDTO() : RequestsListItemDTO{
    return RequestsListItemDTO(id = id, label = label)
}

fun AddRequestModel.toDTO(): AddRequestModelDTO{
    return AddRequestModelDTO(label = label)
}