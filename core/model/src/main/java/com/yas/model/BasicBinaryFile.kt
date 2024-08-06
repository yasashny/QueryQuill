package com.yas.model

import android.net.Uri


sealed class BasicBinaryFile {
    abstract val uri: Uri
    abstract val fileName: String
}