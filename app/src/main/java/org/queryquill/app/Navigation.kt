/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.queryquill.app.core.model.ScreenState
import org.queryquill.app.feature.cookie.CookieScreen
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
    data object CookieScreenRoute: Destinations(route = "cookie")
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
                navigateToCookie = {
                    navController.navigate(Destinations.CookieScreenRoute.route)
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
        composable(Destinations.CookieScreenRoute.route) {
            CookieScreen {
                navController.navigateUp()
            }
        }
    }
}


