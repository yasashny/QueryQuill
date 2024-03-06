package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.ChipGroupSingleLine
import kotlin.reflect.KFunction1

@Composable
fun Auth(requestModel: RequestModel,
         updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
) {
    val selectedIndex = remember { mutableStateOf(0) }
    val options = listOf("No Auth", "Basic Auth")
    ChipGroupSingleLine(selectedIndex = selectedIndex, options = options) {

        selectedIndex.value = it

    }

}