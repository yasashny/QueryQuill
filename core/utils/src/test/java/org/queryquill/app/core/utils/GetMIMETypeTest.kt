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
