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

package org.queryquill.app.core.utils

import android.content.Context
import android.net.Uri
import io.ktor.http.ContentType
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.core.app.ApplicationProvider
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GetMIMETypeTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `empty uri returns octet-stream`() {
        val result = getMIMEType(
            context,
            Uri.EMPTY,
            fileNameResolver = { _, _ -> error("не должен вызываться") }
        )
        assertEquals(ContentType.Application.OctetStream.toString(), result)
    }

    @Test
    fun `stub jpg filename returns image jpeg`() {
        val result = getMIMEType(
            context,
            Uri.parse("any://ignored"),
            fileNameResolver = { _, _ -> "photo.jpg" }
        )
        assertEquals(ContentType.Image.JPEG.toString(), result)
    }

    @Test
    fun `stub html filename is case-insensitive`() {
        val result = getMIMEType(
            context,
            Uri.parse("whatever"),
            fileNameResolver = { _, _ -> "INDEX.HTML" }
        )
        assertEquals(ContentType.Text.Html.toString(), result)
    }

    @Test
    fun `stub unknown extension falls back to octet-stream`() {
        val result = getMIMEType(
            context,
            Uri.parse("content://some/path"),
            fileNameResolver = { _, _ -> "file.abcxyz" }
        )
        assertEquals(ContentType.Application.OctetStream.toString(), result)
    }

    @Test
    fun `stub no extension falls back to octet-stream`() {
        val result = getMIMEType(
            context,
            Uri.parse("content://whatever"),
            fileNameResolver = { _, _ -> "README" }
        )
        assertEquals(ContentType.Application.OctetStream.toString(), result)
    }

    @Test
    fun `stub complex name with dots returns correct type`() {
        val result = getMIMEType(
            context,
            Uri.parse("content://x/y"),
            fileNameResolver = { _, _ -> "folder.sub/video.mp4" }
        )
        assertEquals(ContentType.Video.MP4.toString(), result)
    }
}
