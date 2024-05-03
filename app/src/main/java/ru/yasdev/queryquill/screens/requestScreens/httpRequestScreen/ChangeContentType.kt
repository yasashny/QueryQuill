package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.MutableState
import ru.yasdev.data.utils.getMIMEType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.states.BodyState
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel


fun changeContentType(
    requestModel: RequestModel,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
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
                        KeyValue(contentType, mime)))){
                    openChangeContentTypeDialog.value = Pair(true, mime)
                }
            }else{
                updateRequest(
                    UpdateHttpRequestModel.Header(listOf(
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
            UpdateHttpRequestModel.Header(listOf(
                KeyValue(
                    contentType, "application/x-www-form-urlencoded"
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )

        is BodyState.MultipartForm -> updateRequest(
            UpdateHttpRequestModel.Header(listOf(
                KeyValue(
                    contentType, "multipart/form-data"
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )

        BodyState.NoBody -> updateRequest(
            UpdateHttpRequestModel.Header(requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )

        is BodyState.Text -> updateRequest(
            UpdateHttpRequestModel.Header(listOf(
                KeyValue(
                    contentType, ""
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != contentType })
        )
    }
}