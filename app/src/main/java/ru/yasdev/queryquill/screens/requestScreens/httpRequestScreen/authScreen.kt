package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.MutableState
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.ChipGroupSingleLine
import kotlin.reflect.KFunction1


fun LazyListScope.authScreen(
    requestModel: RequestModel,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    authState: MutableState<Int>
) {
    item {
        val options = listOf("No Auth", "Basic Auth")
        ChipGroupSingleLine(selectedIndex = authState, options = options) {
            authState.value = it
        }
    }



}