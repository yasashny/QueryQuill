package com.yas.queryquill.screens.requestScreens.httpRequestScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import com.yas.model.KeyValue
import com.yas.model.RequestModel
import com.yas.model.BodyState
import com.yas.model.TextType
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel
import com.yas.requests.utils.getMIMEType


fun changeContentType(
    requestModel: RequestModel,
    updateRequest: (UpdateRequestModel) -> Unit,
    bodyState: BodyState,
    openChangeContentTypeDialog: MutableState<Pair<Boolean, String>>,
    context: Context

) {
    val contentType = "Content-Type"
    when (bodyState) {
        is BodyState.BinaryFile -> {
            val mime = getMIMEType(context, bodyState.uri)
            if(requestModel.bodyState is BodyState.BinaryFile){
                if ((bodyState.uri != Uri.EMPTY) && !(requestModel.header.list.contains(
                        KeyValue(contentType, mime)
                    ))){
                    openChangeContentTypeDialog.value = Pair(true, mime)
                }
            }else{
                updateRequest(
                    UpdateRequestModel.Header(listOf(
                        KeyValue(
                            contentType, mime
                        )
                    ) + requestModel.header.list.filter { keyValue ->
                        keyValue.key != contentType
                    })
                )
            }
        }

        is BodyState.FormUrlEncoded -> updateRequest(
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    contentType, "application/x-www-form-urlencoded"
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )

        is BodyState.MultipartForm -> updateRequest(
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    contentType, "multipart/form-data"
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )

        BodyState.NoBody -> updateRequest(
            UpdateRequestModel.Header(requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )

        is BodyState.Text -> updateRequest(
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    contentType, when(bodyState.textType){
                        TextType.JSON -> "application/json"
                        TextType.XML -> "text/xml"
                        TextType.PLAIN -> "text/plain"
                        TextType.OTHER -> ""
                    }
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )
    }
}