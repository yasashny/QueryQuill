package com.yas.queryquill.components.topAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.queryquill.components.ChangeLabelAlertDialog
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenTopAppBarState(
    drawerState: DrawerState,
    requestModelFlow: StateFlow<RequestModel>,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    requestStateFlow: StateFlow<RequestState>
) {
    val requestModel by requestModelFlow.collectAsState()
    val requestState by requestStateFlow.collectAsState()

    val label = requestModel.label

    TopAppBar(title = {
        if (requestState == RequestState.Request) {
            Text(
                label, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        } else {
            Text(
                "QueryQuill", maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }

    }, navigationIcon = {

        val scope = rememberCoroutineScope()

        IconButton(onClick = { scope.launch(Dispatchers.IO) { drawerState.open() } }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
        }

    }, actions = {
        val openDialog = remember {
            mutableStateOf(false)
        }
        val flag = remember {
            mutableStateOf<String?>(null)
        }
        if (flag.value != null) {
            updateRequest(UpdateHttpRequestModel.Label(flag.value as String))
            flag.value = null
        }
        if (openDialog.value) {
            ChangeLabelAlertDialog(openDialog, flag)
        }
        if (requestState == RequestState.Request) {
            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit, contentDescription = null
                )
            }
        } else {
            IconButton(
                onClick = {
                    openDialog.value = true
                }, enabled = false
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit, contentDescription = null
                )
            }
        }

    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    )
    )
}