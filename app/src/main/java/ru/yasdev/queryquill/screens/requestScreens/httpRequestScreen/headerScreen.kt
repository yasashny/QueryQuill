package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.foundation.lazy.LazyListScope
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.editableList
import kotlin.reflect.KFunction1


fun LazyListScope.headerScreen(
    requestModel: RequestModel, updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
) {
    editableList(items = requestModel.header.list){keyValueList ->
        updateRequest(UpdateHttpRequestModel.Header(keyValueList))
    }
}