package org.queryquill.app.feature.response.source

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.core.ui.CodeEditor
import org.queryquill.app.feature.response.R
import org.queryquill.app.feature.response.utils.RestrictiveConstants
import java.io.File

@Composable
internal fun ResponseScreenSource(
    languageType: LanguageType, file: File, state: CodeEditorState
) {
    val context = LocalContext.current

    if (file.length() > RestrictiveConstants.FILE_CANNOT_BE_OPENED) {
        FileCannotBeOpenedScreen()
    } else {

        var confirmFileOpening by remember {
            mutableStateOf(file.length() <= RestrictiveConstants.CONFIRM_FILE_OPENING)
        }

        if (confirmFileOpening) {

            CodeEditor(
                state = state,
                modifier = Modifier.fillMaxSize(),
                isEditable = false,
                languageType = languageType,
                isBasicDisplayMode = true,
                file = file,
                isWordWrap = file.length() <= RestrictiveConstants.DISABLE_WORD_WRAP
            )
            if (file.length() > RestrictiveConstants.DISABLE_WORD_WRAP) {
                Toast.makeText(
                    context,
                    stringResource(R.string.the_file_is_too_big_wordwrap_is_disabled_for_performance_reasons),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            ConfirmFileOpeningScreen {
                confirmFileOpening = true
            }
        }
    }
}