package com.yas.request.query

import androidx.compose.foundation.lazy.LazyListScope
import com.yas.model.RequestModel
import com.yas.model.UpdateRequestModel
import com.yas.request.components.editableList


internal fun LazyListScope.queryScreen(
    requestModel: RequestModel, updateRequest: (UpdateRequestModel) -> Unit
) {
    queryPreview(requestModel = requestModel)
    editableList(items = requestModel.query.list) { keyValueList ->
        updateRequest(UpdateRequestModel.Query(keyValueList))
    }
}