package com.yas.request.header

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.model.RequestModel
import com.yas.request.editableList
import com.yas.request.UpdateRequestModel


fun LazyListScope.headerScreen(
    requestModel: RequestModel, updateRequest: (UpdateRequestModel) -> Unit
) {
    editableList(items = requestModel.header.list){keyValueList ->
        updateRequest(UpdateRequestModel.Header(keyValueList))
    }
}