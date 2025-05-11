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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.queryquill.app.core.model.ScreenState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.cookie.CookieScreen
import org.queryquill.app.feature.new_transaction.AddTransactionDialog
import org.queryquill.app.feature.new_transaction.NewTransactionScreen
import org.queryquill.app.feature.request.RequestScreen
import org.queryquill.app.feature.request_code_editor.RequestCodeEditorScreen
import org.queryquill.app.feature.response.ResponseScreen
import org.queryquill.app.feature.settings.SettingsDialog
import org.queryquill.app.feature.transaction.TransactionScreen
import kotlin.reflect.typeOf

@Serializable
private data object MainScreenRoute

@Serializable
private data class EditorScreenRoute(
    val fileName: String, val textType: TextType
)

@Serializable
private data object CookieScreenRoute

@Composable
fun Navigation(navController: NavHostController, screenState: ScreenState) {

    NavHost(navController = navController, startDestination = MainScreenRoute) {
        composable<MainScreenRoute> {
            TransactionScreen(
                screenState = screenState,
                navigateToEditor = { fileName, textType ->
                    navController.navigate(
                        EditorScreenRoute(fileName, textType)
                    )
                },
                navigateToSettings = { onDismiss ->
                    SettingsDialog(onDismiss)
                },
                navigateToCookie = {
                    navController.navigate(CookieScreenRoute)
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
        composable<EditorScreenRoute>(
            typeMap = mapOf(
                typeOf<TextType>() to NavType.EnumType(TextType::class.java)
            )
        ) {
            val arguments = it.toRoute<EditorScreenRoute>()
            RequestCodeEditorScreen(
                arguments.fileName, arguments.textType
            ) {
                navController.navigateUp()
            }
        }
        composable<CookieScreenRoute> {
            CookieScreen {
                navController.navigateUp()
            }
        }
    }
}


