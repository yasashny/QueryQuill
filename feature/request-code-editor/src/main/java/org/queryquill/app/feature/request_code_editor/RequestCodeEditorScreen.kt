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

package org.queryquill.app.feature.request_code_editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.core.ui.CodeEditor
import org.queryquill.app.core.ui.SaveDataOnStop

@Composable
fun RequestCodeEditorScreen(
    fileName: String, textType: TextType, navigateUp: () -> Unit
) {
    val vm = koinViewModel<RequestCodeEditorViewModel>()
    RequestCodeEditorScreen(
        fileName,
        textType,
        navigateUp,
        vm.codeEditorState,
        codeEditorLoadingState = vm.codeEditorLoadingState,
        transferFileToCodeEditorState = vm::transferFileToCodeEditorState,
        saveData = vm::saveData
    )
}


@Composable
internal fun RequestCodeEditorScreen(
    fileName: String,
    textType: TextType,
    navigateUp: () -> Unit,
    state: CodeEditorState,
    codeEditorLoadingState: Boolean,
    transferFileToCodeEditorState: (fileName: String) -> Unit,
    saveData: (fileName: String) -> Unit
) {
    val languageType = textType.toLanguageType()

    Scaffold(topBar = {
        RequestCodeEditorTopBar(languageType.name, navigateUp)
    }) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            CodeEditor(
                state = state,
                isBasicDisplayMode = false,
                languageType = languageType,
                transferFileToCodeEditorState = {
                    transferFileToCodeEditorState(fileName)
                },
                isLoading = codeEditorLoadingState
            )

            SaveDataOnStop {
                saveData(fileName)
            }
        }
    }
}