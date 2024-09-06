package org.queryquill.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.queryquill.app.core.model.ScreenState
import org.queryquill.app.feature.new_transaction.AddTransactionDialog
import org.queryquill.app.feature.new_transaction.NewTransactionScreen
import org.queryquill.app.feature.request.RequestScreen
import org.queryquill.app.feature.request_code_editor.RequestCodeEditorScreen
import org.queryquill.app.feature.response.ResponseScreen
import org.queryquill.app.feature.settings.SettingsDialog
import org.queryquill.app.feature.transaction.TransactionScreen


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


