package com.yas.queryquill

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yas.model.ScreenState
import com.yas.new_transaction.AddTransactionDialog
import com.yas.new_transaction.NewTransactionScreen
import com.yas.request.RequestScreen
import com.yas.request_code_editor.RequestCodeEditorScreen
import com.yas.response.ResponseScreen
import com.yas.settings.SettingsDialog
import com.yas.transaction.TransactionScreen


sealed class Destinations(
    val route: String
) {
    data object MainScreenRoute : Destinations(route = "main")
    data object EditorScreenRoute : Destinations(route = "editor/{textFileName}/{languageType}")
}


@Composable
fun Navigation(navController: NavHostController, screenState: ScreenState) {

    NavHost(navController = navController, startDestination = Destinations.MainScreenRoute.route) {
        composable(Destinations.MainScreenRoute.route) {
            TransactionScreen(screenState = screenState,
                navigateToEditor = { textFileName, languageType ->
                    navController.navigate("editor/${textFileName}/${languageType}")
                },
                navigateToSettings = { onDismiss ->
                    SettingsDialog(onDismiss)
                },
                openAddTransactionDialog = { onDismiss ->
                    AddTransactionDialog(onDismiss)

                },
                navigateToRequestScreen = { modifier, navigateToEditor, onRequestSent ->
                    RequestScreen(
                        modifier = modifier,
                        navigateToEditor = navigateToEditor,
                        onRequestSent = onRequestSent
                    )
                },
                goToNewTransactionScreen = { NewTransactionScreen() },
                goToResponseScreen = { modifier ->
                    ResponseScreen(
                        modifier = modifier
                    )
                })
        }
        composable(Destinations.EditorScreenRoute.route) { backStackEntry ->
            RequestCodeEditorScreen(
                backStackEntry.arguments?.getString("textFileName")!!,
                backStackEntry.arguments?.getString("languageType")!!
            ) {
                navController.navigateUp()
            }
        }
    }
}


