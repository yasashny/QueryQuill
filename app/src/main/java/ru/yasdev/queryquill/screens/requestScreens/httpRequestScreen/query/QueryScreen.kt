package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.query

import androidx.compose.foundation.lazy.LazyListScope
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.components.editableList
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel


fun LazyListScope.queryScreen(requestModel: RequestModel,
                              updateRequest: (UpdateHttpRequestModel) -> Unit
){
    editableList(items = requestModel.query.list){keyValueList ->
        updateRequest(UpdateHttpRequestModel.Query(keyValueList))
    }
}