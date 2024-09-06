package org.queryquill.app.core.model

sealed class BasicBinaryFile {
    abstract val uri: ImmutableUri
    abstract val fileName: String
}