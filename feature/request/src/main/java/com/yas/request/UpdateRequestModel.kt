package com.yas.request

import android.net.Uri
import com.yas.model.AuthState
import com.yas.model.HttpType
import com.yas.model.KeyValue
import com.yas.model.MultipartFormState
import com.yas.model.TextType
import com.yas.request.auth.EnumAuthState
import com.yas.request.body.EnumBodyState

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