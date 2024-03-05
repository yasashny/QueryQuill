package ru.yasdev.queryquill.components

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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch
import ru.yasdev.domain.requestsDb.states.RequestState
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    label: String,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    requestState: RequestState
) {
    TopAppBar(title = {
        if( requestState == RequestState.Request){
            Text(
                label, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
        else{
            Text(
                "QueryQuill", maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }

    }, navigationIcon = {

        val scope = rememberCoroutineScope()

        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Localized description")
        }


    }, actions = {
        val openDialog = remember {
            mutableStateOf(false)
        }
        val flag = remember {
            mutableStateOf<String?>(null)
        }
        if (flag.value != null){
            updateRequest(UpdateHttpRequestModel.Label(flag.value as String))
            flag.value = null
        }
        if (openDialog.value){
            ChangeLabelAlertDialog(openDialog, flag)
        }
        if (requestState == RequestState.Request){
            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Outlined.Edit, contentDescription = "Localized description"
                )
            }
        }
        else{
            IconButton(onClick = {
                openDialog.value = true
            },
                enabled = false) {
                Icon(
                    imageVector = Icons.Outlined.Edit, contentDescription = "Localized description"
                )
            }
        }

    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    )
        //scrollBehavior = scrollBehavior
    )
}