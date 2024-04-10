package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.BodyState
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import kotlin.reflect.KFunction1

@Composable
fun BodyScreenText(
    bodyState: BodyState.Text, updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
) {
    OutlinedTextField(
        value = bodyState.text,
        onValueChange = { updateRequest(UpdateHttpRequestModel.Body(BodyState.Text(it))) },
        label = @Composable { Text(text = "Json/XML") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            .heightIn(min = 150.dp)
    )
}