package com.yas.response

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yas.model.CodeEditorState
import com.yas.model.LanguageType
import com.yas.ui.CodeEditor
import java.io.File
import java.net.URI

@Composable
internal fun ResponseScreenSource(
    fileName: String,
    languageType: LanguageType,
    getTextFileUri: (textFileName: String) -> URI
) {
    val context = LocalContext.current
    val file = rememberUpdatedState(newValue = File(getTextFileUri(fileName)))

    if (file.value.length() > 104857600) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Files larger than 100 megabytes cannot be previewed due to performance reasons. You can download the file.",
                Modifier.padding(15.dp)
            )
        }
    } else {
        if (file.value.length() > 5242880) {
            var showAnyway by remember {
                mutableStateOf(false)
            }
            if (showAnyway) {
                if (file.value.length() > 52428800) {
                    val state = CodeEditorState()
                    CodeEditor(
                        state = state,
                        modifier = Modifier.fillMaxSize(),
                        isEditable = false,
                        languageType = languageType,
                        isBasicDisplayMode = true,
                        file = file.value,
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
                        file = file.value
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column {
                        Text(
                            text = "A file larger than 5 MB is hidden for performance reasons",
                            Modifier.padding(15.dp)
                        )
                        OutlinedButton(onClick = { showAnyway = true }, Modifier.padding(15.dp)) {
                            Text(text = "Show anyway")
                        }
                    }


                }
            }

        } else {
            val state = CodeEditorState()
            CodeEditor(
                state = state,
                modifier = Modifier.fillMaxSize(),
                isEditable = false,
                languageType = languageType,
                isBasicDisplayMode = true,
                file = file.value
            )
        }
    }
}