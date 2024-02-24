package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.runtime.Composable
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.EditableList
import kotlin.reflect.KFunction1

@Composable
fun Query(requestModel: RequestModel,
          updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
){
    EditableList(items = requestModel.query.list, onValueChanged = {
        updateRequest(UpdateHttpRequestModel.Query(it))
    })
}