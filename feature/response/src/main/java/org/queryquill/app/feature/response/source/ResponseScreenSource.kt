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

package org.queryquill.app.feature.response.source

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.core.ui.CodeEditor
import org.queryquill.app.feature.response.R

@Composable
internal fun ResponseScreenSource(
    languageType: LanguageType,
    state: CodeEditorState,
    fileLength: Long,
    transferFileToCodeEditorState: () -> Unit,
    isCodeEditorLoading: Boolean,
) {
    val context = LocalContext.current
    if (fileLength > RestrictiveConstants.FILE_CANNOT_BE_OPENED) {
        FileCannotBeOpenedScreen()
        return
    }
    var isConfirmed by remember {
        mutableStateOf(fileLength <= RestrictiveConstants.CONFIRM_FILE_OPENING)
    }
    if (!isConfirmed) {
        ConfirmFileOpeningScreen {
            isConfirmed = true
        }
        return
    }
    val wordWrapEnabled = fileLength <= RestrictiveConstants.DISABLE_WORD_WRAP
    CodeEditor(
        state = state,
        modifier = Modifier.fillMaxSize(),
        isEditable = false,
        languageType = languageType,
        isBasicDisplayMode = true,
        transferFileToCodeEditorState = transferFileToCodeEditorState,
        isWordWrap = wordWrapEnabled,
        isLoading = isCodeEditorLoading
    )
    if (!wordWrapEnabled) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                context.getString(R.string.the_file_is_too_big_wordwrap_is_disabled_for_performance_reasons),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

private object RestrictiveConstants {
    const val FILE_CANNOT_BE_OPENED = 104857600
    const val CONFIRM_FILE_OPENING = 5242880
    const val DISABLE_WORD_WRAP = 52428800
}

@Preview
@Composable
private fun PreviewResponseScreenSource() {
    ResponseScreenSource(
        languageType = LanguageType.OTHER,
        state = CodeEditorState(),
        fileLength = 5000L,
        transferFileToCodeEditorState = {},
        isCodeEditorLoading = false
    )
}