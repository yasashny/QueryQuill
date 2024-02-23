package ru.yasdev.queryquill.navigationDrawer

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.yasdev.domain.requestsDb.states.ListOfRequestsState
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.RequestEvent

@Composable
fun NavigationDrawer(viewModel: MainActivityViewModel, composable: @Composable (draverState: DrawerState) -> Unit) {
    Log.d("HHHHH", "ddddd")
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items by viewModel.listOfRequests.collectAsState(initial = ListOfRequestsState.Loading)
    val requestId by viewModel.requestModel.collectAsState()
    //val selectedItem = remember { mutableStateOf() }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Button(onClick = {
                    scope.launch { drawerState.close()
                        viewModel.onEvent(RequestEvent.SetRequest(null))
                    }

                }) {
                    
                }

                when(items){

                    ListOfRequestsState.Loading -> {}
                    is ListOfRequestsState.ListOfRequests -> {
                        when((items as ListOfRequestsState.ListOfRequests).list.size){
                            0 -> {
                                Text(text = "Create new request")}
                            else -> {
                                LazyColumn{
                                    items((items as ListOfRequestsState.ListOfRequests).list) { item ->
                                        NavigationDrawerItem(
                                            //icon = { Icon(item, contentDescription = null) },
                                            label = { Text(item.label) },
                                            selected = requestId.id == item.id,
                                            onClick = {

                                                scope.launch { drawerState.close()
                                                    viewModel.onEvent(RequestEvent.SetRequest(item.id))
                                                }




                                            },
                                            badge = { IconButton(onClick = { viewModel.onEvent(RequestEvent.DeleteRequest(item.id)) }) {
                                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "sdsd")
                                            }},
                                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                        )
                                    }
                                }

                            }


                        }






                    }
                }
            }
        },
        content = {
            composable(drawerState)
        }
    )
}