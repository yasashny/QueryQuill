package com.yas.queryquill.screens.requestScreens.httpRequestScreen.header

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.model.RequestModel
import com.yas.queryquill.components.editableList
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel


fun LazyListScope.headerScreen(
    requestModel: RequestModel, updateRequest: (UpdateRequestModel) -> Unit
) {
    editableList(items = requestModel.header.list){keyValueList ->
        updateRequest(UpdateRequestModel.Header(keyValueList))
    }
}