package org.queryquill.app.feature.response.preview

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.InputStream

@Composable
internal fun saveFileLauncher(file: File): ManagedActivityResultLauncher<String, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(contract = ActivityResultContracts.CreateDocument("*/*")) { uri: Uri? ->
        uri?.let {
            val inputStream: InputStream = file.inputStream()
            context.contentResolver.openOutputStream(it)?.use { outputStream ->
                inputStream.copyTo(outputStream)
                inputStream.close()
            }
        }
    }
}