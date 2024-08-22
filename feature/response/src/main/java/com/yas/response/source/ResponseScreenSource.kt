package com.yas.response.source

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.yas.model.CodeEditorState
import com.yas.model.LanguageType
import com.yas.response.utils.RestrictiveConstants
import com.yas.ui.CodeEditor
import java.io.File

@Composable
internal fun ResponseScreenSource(
    languageType: LanguageType, file: File
) {
    val context = LocalContext.current

    if (file.length() > RestrictiveConstants.FILE_CANNOT_BE_OPENED) {
        FileCannotBeOpenedScreen()
    } else {

        var confirmFileOpening by remember {
            mutableStateOf(file.length() <= RestrictiveConstants.CONFIRM_FILE_OPENING)
        }

        if (confirmFileOpening) {
            if (file.length() > RestrictiveConstants.DISABLE_WORD_WRAP) {
                val state = CodeEditorState()
                CodeEditor(
                    state = state,
                    modifier = Modifier.fillMaxSize(),
                    isEditable = false,
                    languageType = languageType,
                    isBasicDisplayMode = true,
                    file = file,
                    isWordWrap = false
                )
                Toast.makeText(
                    context,
                    "The file is too big. Wordwrap is disabled for performance reasons",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val state = CodeEditorState()
                CodeEditor(
                    state = state,
                    modifier = Modifier.fillMaxSize(),
                    isEditable = false,
                    languageType = languageType,
                    isBasicDisplayMode = true,
                    file = file
                )
            }
        } else {
            ConfirmFileOpeningScreen {
                confirmFileOpening = true
            }
        }
    }
}