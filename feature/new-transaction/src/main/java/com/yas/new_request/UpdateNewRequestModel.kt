package com.yas.new_request

internal sealed interface UpdateNewRequestModel {
    data class UpdateLabel(val label: String) : UpdateNewRequestModel
}