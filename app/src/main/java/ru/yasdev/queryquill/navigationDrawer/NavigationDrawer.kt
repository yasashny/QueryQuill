package ru.yasdev.queryquill.navigationDrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestEvent
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestViewModel

@Composable
fun NavigationDrawer(
    viewModel: RequestViewModel, composable: @Composable (drawerState: DrawerState) -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items by viewModel.listOfRequests.collectAsState(initial = ListOfRequestsState.Loading)
    val requestId by viewModel.requestModel.collectAsState()
    var gesturesState by remember {
        mutableStateOf(false)
    }
    if(drawerState.isOpen){
        gesturesState = true
    }
    if(drawerState.isClosed){
        gesturesState = false
    }
    ModalNavigationDrawer(drawerState = drawerState, gesturesEnabled = gesturesState, drawerContent = {
        ModalDrawerSheet {
            Spacer(Modifier.height(12.dp))
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                }
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(RequestEvent.SetRequest(null))
                        }
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .height(50.dp)
                ) {
                    Text(
                        text = "New request",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
            Spacer(Modifier.height(12.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
            Spacer(Modifier.height(12.dp))
            when (items) {
                ListOfRequestsState.Loading -> {}
                is ListOfRequestsState.ListOfRequests -> {
                    when ((items as ListOfRequestsState.ListOfRequests).list.size) {
                        0 -> {
                            Text(text = "Create new request")
                        }

                        else -> {
                            LazyColumn {
                                items((items as ListOfRequestsState.ListOfRequests).list) { item ->
                                    NavigationDrawerItem(
                                        label = { Text(item.label) },
                                        selected = requestId.id == item.id,
                                        onClick = {
                                            scope.launch {
                                                drawerState.close()
                                                viewModel.onEvent(RequestEvent.SetRequest(item.id))
                                            }
                                        },
                                        badge = {
                                            IconButton(onClick = {
                                                viewModel.onEvent(
                                                    RequestEvent.DeleteRequest(item.id)
                                                )
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Delete,
                                                    contentDescription = null
                                                )
                                            }
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }, content = {
        composable(drawerState)
    })
}