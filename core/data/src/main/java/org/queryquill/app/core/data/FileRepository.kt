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

package org.queryquill.app.core.data

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class FileRepository(private val context: Context, private val ioDispatcher: CoroutineDispatcher) {

    suspend fun createFileIfNotExist(fileName: String) {
        withContext(ioDispatcher) {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) {
                file.createNewFile()
            }
        }
    }

    suspend fun deleteFile(fileName: String) = withContext(ioDispatcher) {
        runCatching {
            val file = File(context.filesDir, fileName)
            file.delete()
        }
    }

    suspend fun getFileLength(fileName: String): Result<Long> = withContext(ioDispatcher) {
        runCatching {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) throw FileNotFoundException()
            file.length()
        }
    }

    suspend fun getFileUri(fileName: String): Result<Uri> = withContext(ioDispatcher) {
        runCatching {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) throw FileNotFoundException()
            file.toUri()
        }
    }


    private fun InputStream.readInChunks(chunkSize: Int = 10_000): Sequence<String> = sequence {
        val buffer = ByteArray(chunkSize)
        var bytesRead: Int
        while (read(buffer).also { bytesRead = it } != -1) {
            yield(String(buffer, 0, bytesRead))
        }
    }

    fun getChunkedText(fileName: String) = flow {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) throw FileNotFoundException()
        file.inputStream().use { input ->
            input.readInChunks(10_000).forEach { chunk ->
                emit(chunk)

            }
        }
    }.catch {}.flowOn(ioDispatcher)

    suspend fun saveFileLauncher(fileName: String, uri: Uri) {
        withContext(ioDispatcher) {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) throw FileNotFoundException()
            val inputStream: InputStream = file.inputStream()
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                inputStream.copyTo(outputStream)
                inputStream.close()
            }
        }
    }

    suspend fun saveFileFromFlow(fileName: String, dataFlow: Flow<ByteArray>) {
        withContext(ioDispatcher) {
            val file = File(context.filesDir, fileName)
            file.writeText("")
            dataFlow.collect { bytes ->
                file.appendBytes(bytes)
            }
        }
    }
}