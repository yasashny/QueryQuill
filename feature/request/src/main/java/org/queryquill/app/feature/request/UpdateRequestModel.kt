package org.queryquill.app.feature.request

import android.net.Uri
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.auth.EnumAuthState
import org.queryquill.app.feature.request.body.EnumBodyState

internal sealed interface UpdateRequestModel {
    data object Body {
        data class UpdateTextType(val textType: TextType) : UpdateRequestModel
        data class ChangeType(val newState: EnumBodyState) : UpdateRequestModel
        data class FormUrlEncoded(val list: List<KeyValue>) : UpdateRequestModel
        data class MultipartForm(val list: List<MultipartFormState>) : UpdateRequestModel
        data class BinaryFile(
            val uri: Uri,
            val fileName: String,
            val isChangeContentType: Boolean,
            val contentType: String
        ) : UpdateRequestModel
    }

    data class Header(val list: List<KeyValue>) : UpdateRequestModel
    data class Query(val list: List<KeyValue>) : UpdateRequestModel
    data class Type(val type: HttpType) : UpdateRequestModel
    data class Url(val url: String) : UpdateRequestModel
    data object Auth {
        data class ChangeType(val authState: EnumAuthState) : UpdateRequestModel
        data class Basic(val basicState: AuthState.Basic) : UpdateRequestModel
    }
}