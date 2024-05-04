package com.yas.domain.sendRequest

import com.yas.domain.requestsDb.models.RequestModel

fun RequestModel.toSendRequestModel(): SendRequestModel{
    return SendRequestModel(
        bodyState = bodyState,
        query = query.list,
        headers = header.list,
        auth = auth,
        url = url,
        type = type
    )
}