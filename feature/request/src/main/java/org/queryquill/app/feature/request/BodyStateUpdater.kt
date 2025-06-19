/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.request

import android.net.Uri
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.utils.toMimeType


internal object BodyStateUpdater {

    private const val CONTENT_TYPE = "Content-Type"

    suspend fun updateBody(
        currentState: RequestModel,
        updateBody: UpdateRequest.Body,
        getFileSize: suspend (fileName: String) -> Result<Long>,
        deleteFile: suspend (fileName: String) -> Unit,
    ): RequestUiState? {
        return when (updateBody) {
            is UpdateRequest.Body.BinaryFile -> when (updateBody) {
                is UpdateRequest.Body.BinaryFile.File -> updateBinaryFile(
                    uri = updateBody.uri,
                    fileName = updateBody.fileName,
                    contentType = updateBody.contentType,
                    currentState = currentState,
                    showChangeTypeDialog = updateBody.showChangeTypeDialog
                )

                is UpdateRequest.Body.BinaryFile.ChangeContentTypeInHeaders -> {
                    changeContentTypeInHeaders(
                        contentType = updateBody.contentType, currentState = currentState
                    )
                }
            }

            is UpdateRequest.Body.ChangeType -> changeBodyType(
                newState = updateBody.newState,
                currentState = currentState,
                getFileSize = getFileSize,
                showDialog = updateBody.onDirtyBody,
                force = updateBody.force,
                deleteFile = deleteFile
            )

            is UpdateRequest.Body.FormUrlEncoded -> updateFormUrlEncoded(
                updateType = updateBody.updateType,
                item = updateBody.item,
                currentState = currentState
            )

            is UpdateRequest.Body.MultipartForm -> updateMultipartForm(
                updateType = updateBody.updateType,
                item = updateBody.item,
                currentState = currentState
            )

            is UpdateRequest.Body.TextType -> {
                if (currentState.bodyState !is BodyState.Text) {
                    return RequestUiState.Success(currentState)
                }
                updateTextType(
                    textType = updateBody.textType,
                    bodyState = currentState.bodyState as BodyState.Text,
                    currentState = currentState
                )
            }
        }
    }

    private fun updateMultipartForm(
        updateType: UpdateRequest.UpdateType, item: MultipartFormState, currentState: RequestModel
    ): RequestUiState {
        return if (currentState.bodyState !is BodyState.MultipartForm) {
            RequestUiState.Success(currentState)
        } else {
            val result =
                (currentState.bodyState as BodyState.MultipartForm).multipart.toMutableList()
            when (updateType) {
                UpdateRequest.UpdateType.DELETE -> {
                    result.removeAll { it.id == item.id }
                }

                UpdateRequest.UpdateType.UPDATE -> {
                    val index = result.indexOfFirst { it.id == item.id }
                    if (index >= 0) {
                        result[index] = item
                        if (index == result.lastIndex) {
                            result += MultipartFormState.Text()
                        }
                    }
                }
            }
            RequestUiState.Success(
                currentState.copy(
                    bodyState = BodyState.MultipartForm(
                        multipart = result.toList()
                    )
                )
            )
        }
    }

    private fun updateTextType(
        textType: TextType, bodyState: BodyState.Text, currentState: RequestModel
    ): RequestUiState {
        return RequestUiState.Success(
            currentState.copy(
                bodyState = bodyState.copy(textType = textType), header = listOf(
                    KeyValue(
                        CONTENT_TYPE, textType.toMimeType()
                    )
                ) + currentState.header.filter { keyValue -> keyValue.key != CONTENT_TYPE })
        )
    }

    private fun updateFormUrlEncoded(
        updateType: UpdateRequest.UpdateType, item: KeyValue, currentState: RequestModel
    ): RequestUiState {
        return if (currentState.bodyState !is BodyState.FormUrlEncoded) {
            RequestUiState.Success(currentState)
        } else {
            RequestUiState.Success(
                currentState.copy(
                    bodyState = BodyState.FormUrlEncoded(
                        list = KeyValueListUpdater.update(
                            updateType,
                            item,
                            (currentState.bodyState as BodyState.FormUrlEncoded).list
                        )
                    )
                )
            )
        }
    }

    private fun changeContentTypeInHeaders(
        contentType: String, currentState: RequestModel
    ): RequestUiState {
        return RequestUiState.Success(
            currentState.copy(
                header = listOf(
                    KeyValue(
                        CONTENT_TYPE, contentType
                    )
                ) + currentState.header.filter { keyValue ->
                    keyValue.key != CONTENT_TYPE
                })
        )
    }

    private fun updateBinaryFile(
        uri: Uri,
        fileName: String,
        contentType: String,
        currentState: RequestModel,
        showChangeTypeDialog: () -> Unit,
    ): RequestUiState {
        val newState = currentState.copy(
            bodyState = BodyState.BinaryFile(
                uri, fileName
            )
        )
        if (!currentState.header.contains(
                KeyValue(
                    key = CONTENT_TYPE, value = contentType
                )
            )
        ) {
            showChangeTypeDialog()
        }
        return RequestUiState.Success(newState)
    }

    private suspend fun isBodyStateDirty(
        bodyState: BodyState, getFileSize: suspend (fileName: String) -> Result<Long>
    ): Boolean {
        return when (bodyState) {
            is BodyState.NoBody -> false
            is BodyState.Text -> getFileSize(bodyState.textFileName).getOrElse { 0L } > 0L
            is BodyState.FormUrlEncoded -> bodyState.list.size != 1 || bodyState.list[0].key.isNotEmpty() || bodyState.list[0].value.isNotEmpty()
            is BodyState.MultipartForm -> bodyState.multipart.size != 1
            is BodyState.BinaryFile -> bodyState != BodyState.BinaryFile()
        }
    }

    private fun updateBodyType(
        newState: BodyState.Type, currentState: RequestModel,
    ): RequestUiState {
        return when (newState) {
            BodyState.Type.NoBody -> {
                RequestUiState.Success(
                    currentState.copy(
                        bodyState = BodyState.NoBody,
                        header = currentState.header.filter { keyValue -> keyValue.key != CONTENT_TYPE })
                )
            }

            BodyState.Type.Text -> {
                RequestUiState.Success(
                    currentState.copy(
                        bodyState = BodyState.Text(textFileName = "${currentState.id}_request.txt"),
                        header = listOf(
                            KeyValue(
                                CONTENT_TYPE, BodyState.Text().textType.toMimeType()
                            )
                        ) + currentState.header.filter { keyValue -> keyValue.key != CONTENT_TYPE })
                )
            }

            BodyState.Type.FormUrlEncoded -> {
                RequestUiState.Success(
                    currentState.copy(
                        bodyState = BodyState.FormUrlEncoded(), header = listOf(
                            KeyValue(
                                CONTENT_TYPE, "application/x-www-form-urlencoded"
                            )
                        ) + currentState.header.filter { keyValue -> keyValue.key != CONTENT_TYPE })
                )
            }

            BodyState.Type.MultipartForm -> {
                RequestUiState.Success(
                    currentState.copy(
                        bodyState = BodyState.MultipartForm(), header = listOf(
                            KeyValue(
                                CONTENT_TYPE, "multipart/form-data"
                            )
                        ) + currentState.header.filter { keyValue -> keyValue.key != CONTENT_TYPE })
                )
            }

            BodyState.Type.BinaryFile -> {
                RequestUiState.Success(
                    currentState.copy(
                        bodyState = BodyState.BinaryFile(), header = listOf(
                            KeyValue(
                                CONTENT_TYPE, "application/octet-stream"
                            )
                        ) + currentState.header.filter { keyValue -> keyValue.key != CONTENT_TYPE })
                )
            }
        }
    }

    private suspend fun changeBodyType(
        newState: BodyState.Type,
        currentState: RequestModel,
        getFileSize: suspend (fileName: String) -> Result<Long>,
        deleteFile: suspend (fileName: String) -> Unit,
        showDialog: () -> Unit,
        force: Boolean
    ): RequestUiState? {
        if (currentState.bodyState.type == newState) {
            return null
        }
        if (!force) {
            val isDirty = isBodyStateDirty(
                bodyState = currentState.bodyState, getFileSize = getFileSize
            )
            if (isDirty) {
                showDialog()
                return null
            } else {
                return updateBodyType(newState = newState, currentState = currentState)
            }
        } else {
            if (currentState.bodyState is BodyState.Text) {
                deleteFile((currentState.bodyState as BodyState.Text).textFileName)
            }
            return updateBodyType(newState = newState, currentState = currentState)
        }
    }
}