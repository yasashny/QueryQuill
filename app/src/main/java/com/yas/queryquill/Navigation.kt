package com.yas.queryquill

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yas.model.ScreenState
import com.yas.request_code_editor.RequestCodeEditorScreen
import com.yas.settings.SettingsScreen
import com.yas.transaction.TransactionScreen


sealed class Destinations(
    val route: String
) {
    data object MainScreenRoute : Destinations(route = "main")
    data object EditorScreenRoute : Destinations(route = "editor")
    data object SettingsScreenRoute : Destinations(route = "settings")
}


@Composable
fun Navigation(navController: NavHostController, screenState: ScreenState) {

    NavHost(navController = navController, startDestination = Destinations.MainScreenRoute.route) {
        composable(Destinations.MainScreenRoute.route) {
            TransactionScreen(screenState = screenState, navigateToEditor = {
                navController.navigate(Destinations.EditorScreenRoute.route)
            }, navigateToSettings = {
                navController.navigate(Destinations.SettingsScreenRoute.route)
            })
        }
        composable(Destinations.EditorScreenRoute.route) {
            RequestCodeEditorScreen{
                navController.navigateUp()
            }
        }
        composable(Destinations.SettingsScreenRoute.route) {
            SettingsScreen{
                navController.navigateUp()
            }
        }
    }
}


