package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import kotlin.reflect.KFunction1

@Composable
fun Auth(requestModel: RequestModel,
         updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
) {
    Text(text = "Auth")
}