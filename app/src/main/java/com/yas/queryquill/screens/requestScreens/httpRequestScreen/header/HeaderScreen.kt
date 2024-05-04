package com.yas.queryquill.screens.requestScreens.httpRequestScreen.header

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.queryquill.components.editableList
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel


fun LazyListScope.headerScreen(
    requestModel: RequestModel, updateRequest: (UpdateHttpRequestModel) -> Unit
) {
    editableList(items = requestModel.header.list){keyValueList ->
        updateRequest(UpdateHttpRequestModel.Header(keyValueList))
    }
}