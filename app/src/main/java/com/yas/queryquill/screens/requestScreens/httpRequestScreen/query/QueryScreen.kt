package com.yas.queryquill.screens.requestScreens.httpRequestScreen.query

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.model.RequestModel
import com.yas.queryquill.components.editableList
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel


fun LazyListScope.queryScreen(
    requestModel: RequestModel, updateRequest: (UpdateRequestModel) -> Unit
) {
    queryPreview(requestModel = requestModel)
    editableList(items = requestModel.query.list) { keyValueList ->
        updateRequest(UpdateRequestModel.Query(keyValueList))
    }
}