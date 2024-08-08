package com.yas.request.utils

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import com.yas.model.BodyState
import com.yas.model.KeyValue
import com.yas.model.RequestModel
import com.yas.model.TextType
import com.yas.request.UpdateRequestModel
import com.yas.requests.utils.getMIMEType

internal fun changeContentType(
    requestModel: RequestModel,
    updateRequest: (UpdateRequestModel) -> Unit,
    bodyState: BodyState,
    openChangeContentTypeDialog: MutableState<Pair<Boolean, String>>,
    context: Context

) {
    when (bodyState) {
        is BodyState.BinaryFile -> {
            val mime = getMIMEType(context, bodyState.uri)
            if (requestModel.bodyState is BodyState.BinaryFile) {
                if ((bodyState.uri != Uri.EMPTY) && !(requestModel.header.list.contains(
                        KeyValue(Constants.CONTENT_TYPE, mime)
                    ))
                ) {
                    openChangeContentTypeDialog.value = Pair(true, mime)
                }
            } else {
                updateRequest(
                    UpdateRequestModel.Header(listOf(
                        KeyValue(
                            Constants.CONTENT_TYPE, mime
                        )
                    ) + requestModel.header.list.filter { keyValue ->
                        keyValue.key != Constants.CONTENT_TYPE
                    })
                )
            }
        }

        is BodyState.FormUrlEncoded -> updateRequest(
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    Constants.CONTENT_TYPE, "application/x-www-form-urlencoded"
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
        )

        is BodyState.MultipartForm -> updateRequest(
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    Constants.CONTENT_TYPE, "multipart/form-data"
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
        )

        BodyState.NoBody -> updateRequest(
            UpdateRequestModel.Header(requestModel.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
        )

        is BodyState.Text -> updateRequest(
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    Constants.CONTENT_TYPE, when (bodyState.textType) {
                        TextType.JSON -> "application/json"
                        TextType.XML -> "text/xml"
                        TextType.PLAIN -> "text/plain"
                        TextType.OTHER -> ""
                    }
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
        )
    }
}