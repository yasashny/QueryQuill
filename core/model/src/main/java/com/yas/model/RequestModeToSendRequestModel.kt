package com.yas.model

fun com.yas.model.RequestModel.toSendRequestModel(): SendRequestModel {
    return SendRequestModel(
        bodyState = bodyState,
        query = query.list,
        headers = header.list,
        auth = auth,
        url = url,
        type = type
    )
}