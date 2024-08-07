package com.yas.request.query

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.model.RequestModel
import com.yas.request.editableList
import com.yas.request.UpdateRequestModel


fun LazyListScope.queryScreen(
    requestModel: RequestModel, updateRequest: (UpdateRequestModel) -> Unit
) {
    queryPreview(requestModel = requestModel)
    editableList(items = requestModel.query.list) { keyValueList ->
        updateRequest(UpdateRequestModel.Query(keyValueList))
    }
}