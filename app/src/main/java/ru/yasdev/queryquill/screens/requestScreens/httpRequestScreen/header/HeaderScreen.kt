package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.header

import androidx.compose.foundation.lazy.LazyListScope
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.components.editableList
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel


fun LazyListScope.headerScreen(
    requestModel: RequestModel, updateRequest: (UpdateHttpRequestModel) -> Unit
) {
    editableList(items = requestModel.header.list){keyValueList ->
        updateRequest(UpdateHttpRequestModel.Header(keyValueList))
    }
}