package com.yas.queryquill.screens.requestScreens.httpRequestScreen.query

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.queryquill.components.editableList
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel


fun LazyListScope.queryScreen(
    requestModel: RequestModel, updateRequest: (UpdateHttpRequestModel) -> Unit
) {
    queryPreview(requestModel = requestModel)
    editableList(items = requestModel.query.list) { keyValueList ->
        updateRequest(UpdateHttpRequestModel.Query(keyValueList))
    }
}