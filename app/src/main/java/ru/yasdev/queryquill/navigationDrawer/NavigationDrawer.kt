package ru.yasdev.queryquill.navigationDrawer

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.utils.LastIdState
import ru.yasdev.domain.utils.ListOfRequestsState
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.RequestEvent

@Composable
fun NavigationDrawer(viewModel: MainActivityViewModel, composable: @Composable (draverState: DrawerState) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items by viewModel.listOfRequests.collectAsState(initial = ListOfRequestsState.Loading)
    val requestId by viewModel.requestId.collectAsState()
    //val selectedItem = remember { mutableStateOf() }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Button(onClick = { viewModel.onEvent(RequestEvent.AddRequest(AddRequestModel("dufgdufhds"))) }) {
                    
                }

                when(items){

                    ListOfRequestsState.Loading -> {}
                    is ListOfRequestsState.ListOfRequests -> {
                        when((items as ListOfRequestsState.ListOfRequests).list.size){
                            0 -> {
                                Text(text = "Create new request")}
                            else -> {
                                (items as ListOfRequestsState.ListOfRequests).list.forEach { item ->
                                    NavigationDrawerItem(
                                        //icon = { Icon(item, contentDescription = null) },
                                        label = { Text(item.label) },
                                        selected = requestId == LastIdState.Id(item.id),
                                        onClick = {
                                            scope.launch { drawerState.close() }
//                                            if (LastIdState.Id(item.id) != viewModel.requestId.value){
                                                viewModel.onEvent(RequestEvent.UpdateId(LastIdState.Id(id = item.id)))
//                                            }

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
        },
        content = {
            composable(drawerState)
        }
    )
}