package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.Auth
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.AuthScreenAlertDialog
import ru.yasdev.queryquill.components.ChipGroupSingleLine
import kotlin.reflect.KFunction1


fun LazyListScope.authScreen(
    requestModel: RequestModel,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    authState: MutableState<Int>
) {
    when (requestModel.auth) {
        Auth.NoAuth -> {
            authState.value = 0
        }

        is Auth.Basic -> {
            authState.value = 1
        }
    }


    item {
        val openDialog = remember {
            mutableStateOf(Pair(false, 0))
        }
        val isChangeAuthState = remember {
            mutableStateOf(Pair(false, 0))
        }
        if (openDialog.value.first) {
            AuthScreenAlertDialog(isChangeAuthState, openDialog)
        }
        if (isChangeAuthState.value.first) {
            changeAuthType(updateRequest = updateRequest, index = isChangeAuthState.value.second)
            isChangeAuthState.value = Pair(false, isChangeAuthState.value.second)
        }

        Box(Modifier.padding(start = 15.dp, top = 15.dp, bottom = 15.dp)) {
            val options = listOf("No Auth", "Basic Auth")
            ChipGroupSingleLine(selectedIndex = authState, options = options) { index ->
                if (authState.value != index) {
                    when (requestModel.auth) {
                        Auth.NoAuth -> {
                            changeAuthType(updateRequest, index)
                        }

                        is Auth.Basic -> {
                            if (((requestModel.auth as Auth.Basic).password == "") and ((requestModel.auth as Auth.Basic).userName == "")) {
                                changeAuthType(updateRequest, index)
                            } else {
                                openDialog.value = Pair(true, index)
                            }
                        }
                    }
                }
            }
        }

    }

    when (requestModel.auth) {

        Auth.NoAuth -> {}
        is Auth.Basic -> {
            item {

                OutlinedTextField(
                    value = (requestModel.auth as Auth.Basic).userName, onValueChange = {
                        updateRequest(
                            UpdateHttpRequestModel.Auth(
                                Auth.Basic(
                                    it,
                                    (requestModel.auth as Auth.Basic).password
                                )
                            )
                        )
                    },
                    label = { Text(text = "User name") }, modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp).fillMaxWidth()
                )


            }
            item {
                OutlinedTextField(
                    value = (requestModel.auth as Auth.Basic).password, onValueChange = {
                        updateRequest(
                            UpdateHttpRequestModel.Auth(
                                Auth.Basic(
                                    (requestModel.auth as Auth.Basic).userName,
                                    it
                                )
                            )
                        )
                    },
                    label = { Text(text = "Password") }, modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp).fillMaxWidth()
                )
            }
        }

    }


}

private fun changeAuthType(
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    index: Int
) {
    when (index) {
        0 -> {
            updateRequest(UpdateHttpRequestModel.Auth(Auth.NoAuth))
        }

        1 -> {
            updateRequest(UpdateHttpRequestModel.Auth(Auth.Basic("", "")))
        }

    }
}