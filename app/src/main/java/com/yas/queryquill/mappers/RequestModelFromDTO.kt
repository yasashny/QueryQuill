package com.yas.queryquill.mappers

import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.ImmutableList
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

fun RequestDTO.toModel() : RequestModel {
    return RequestModel(
        id = id,
        label = label,
        bodyState = bodyState.toModel(),
        header = ImmutableList(list = header.map { it.toModel() }) ,
        query = ImmutableList(list = query.map { it.toModel() }),
        auth = authState.toModel(),
        type = type.toModel(),
        url = url
    )
}

private fun BodyStateDTO.toModel(): BodyState {
    return when(this){

        is BodyStateDTO.BinaryFile -> BodyState.BinaryFile(uri = uri, fileName = fileName)
        is BodyStateDTO.FormUrlEncoded -> BodyState.FormUrlEncoded(list = list.map { it.toModel() })
        is BodyStateDTO.MultipartForm -> BodyState.MultipartForm(multipart = multipart.map { it.toModel() })
        BodyStateDTO.NoBody -> BodyState.NoBody
        is BodyStateDTO.Text ->BodyState.Text(text = text, textType = textType.toModel())
    }
}

private fun KeyValueDTO.toModel(): KeyValue {
    return KeyValue(key = key, value = value)
}

private fun MultipartFormStateDTO.toModel(): MultipartFormState {
    return when(this){
        is MultipartFormStateDTO.BinaryFile -> MultipartFormState.BinaryFile(uri = uri, fileName = fileName, title = title)
        is MultipartFormStateDTO.Text -> MultipartFormState.Text(keyValue = keyValue.toModel())
    }
}

private fun HttpTypeDTO.toModel() : HttpType {
    return when(this){
        HttpTypeDTO.GET -> HttpType.GET
        HttpTypeDTO.POST -> HttpType.POST
        HttpTypeDTO.PUT -> HttpType.PUT
        HttpTypeDTO.PATCH -> HttpType.PATCH
        HttpTypeDTO.DELETE -> HttpType.DELETE
        HttpTypeDTO.OPTIONS -> HttpType.OPTIONS
        HttpTypeDTO.HEAD -> HttpType.HEAD
    }
}

private fun TextTypeDTO.toModel() : TextType {
    return when(this){
        TextTypeDTO.JSON -> TextType.JSON
        TextTypeDTO.XML -> TextType.XML
        TextTypeDTO.PLAIN -> TextType.PLAIN
        TextTypeDTO.OTHER -> TextType.OTHER
    }
}

private fun AuthStateDTO.toModel(): AuthState {
    return when(this){
        is AuthStateDTO.Basic -> AuthState.Basic(userName = userName, password = password)
        AuthStateDTO.NoAuth -> AuthState.NoAuth
    }
}

fun ResponseDTO.toModel() : ResponseModel {
    return ResponseModel(
        status = status,
        body = body,
        contentLength = contentLength,
        time = time,
        contentType = contentType,
        contentSubtype = contentSubtype,
        headers = headers.map { it.toModel() }
    )
}

fun RequestsListItemDTO.toModel(): RequestsListItem{
    return RequestsListItem(id = id, label = label)
}

fun AddRequestModelDTO.toModel(): AddRequestModel {
    return AddRequestModel(label = label)
}