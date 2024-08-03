package com.yas.queryquill.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.sendRequest.ResponseModel
import com.yas.queryquill.adaptive.ScreenState
import com.yas.queryquill.screens.mainScreen.MainScreen
import com.yas.queryquill.screens.requestScreens.RequestCodeEditorScreen
import com.yas.queryquill.screens.requestScreens.viewModel.RequestEvent
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.ResponseState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel
import com.yas.settings.SettingsScreen
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction1


sealed class Destinations(
    val route: String
) {
    data object MainScreenRoute : Destinations(route = "main")
    data object EditorScreenRoute : Destinations(route = "editor")
    data object SettingsScreenRoute : Destinations(route = "settings")
}


@Composable
fun Navigation(
    navController: NavHostController,
    screenState: ScreenState,
    requestState: StateFlow<RequestState>,
    updateRequest: KFunction1<UpdateRequestModel, Unit>,
    sendRequest: suspend (RequestModel) -> Unit,
    onEvent: KFunction1<RequestEvent, Unit>,
    responseState: StateFlow<ResponseState>
) {
    NavHost(navController = navController, startDestination = Destinations.MainScreenRoute.route) {
        composable(Destinations.MainScreenRoute.route) {
            MainScreen(screenState = screenState,
                requestState = requestState,
                updateRequest = updateRequest,
                onEvent = onEvent,
                sendRequest = sendRequest,
                responseState = responseState,
                navigateToEditor = {
                    navController.navigate(Destinations.EditorScreenRoute.route)
                })
        }
        composable(Destinations.EditorScreenRoute.route) {
            RequestCodeEditorScreen(requestState, updateRequest)
        }
        composable(Destinations.SettingsScreenRoute.route) {
            SettingsScreen()
        }
    }
}


