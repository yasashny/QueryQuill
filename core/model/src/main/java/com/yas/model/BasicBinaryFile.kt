package com.yas.model

sealed class BasicBinaryFile {
    abstract val uri: ImmutableUri
    abstract val fileName: String
}