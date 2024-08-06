package com.yas.queryquill.components.topAppBar

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.yas.queryquill.navigation.Destinations
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("RestrictedApi")
@Composable
fun MyTopAppBar(
    drawerState: DrawerState,
    updateRequest: (UpdateRequestModel) -> Unit,
    requestStateFlow: StateFlow<RequestState>,
    navController: NavHostController
) {

    val navControllerState by navController.currentBackStack.collectAsState()
    val currentScreen = navControllerState.lastOrNull()?.destination?.route

    when (currentScreen) {
        Destinations.MainScreenRoute.route -> MainScreenTopAppBarState(
            drawerState = drawerState,
            updateRequest = updateRequest,
            requestStateFlow = requestStateFlow
        )

        Destinations.EditorScreenRoute.route -> {
            EditorTopAppBarState(
                requestStateFlow = requestStateFlow, navController = navController
            )
        }
        Destinations.SettingsScreenRoute.route -> {
            SettingsTopAppBarState(navController = navController)
        }
    }
}