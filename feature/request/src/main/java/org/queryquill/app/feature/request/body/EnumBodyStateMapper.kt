package org.queryquill.app.feature.request.body

import org.queryquill.app.core.model.BodyState

internal fun BodyState.toEnum(): EnumBodyState {
    return when (this) {
        is BodyState.BinaryFile -> EnumBodyState.BinaryFile
        is BodyState.FormUrlEncoded -> EnumBodyState.FormUrlEncoded
        is BodyState.MultipartForm -> EnumBodyState.MultipartForm
        BodyState.NoBody -> EnumBodyState.NoBody
        is BodyState.Text -> EnumBodyState.Text
    }
}

internal fun EnumBodyState.toBodyState(id: Long): BodyState {
    return when (this) {
        EnumBodyState.NoBody -> BodyState.NoBody
        EnumBodyState.Text -> BodyState.Text.default(id)
        EnumBodyState.FormUrlEncoded -> BodyState.FormUrlEncoded.default()
        EnumBodyState.MultipartForm -> BodyState.MultipartForm.default()
        EnumBodyState.BinaryFile -> BodyState.BinaryFile.default()
    }
}