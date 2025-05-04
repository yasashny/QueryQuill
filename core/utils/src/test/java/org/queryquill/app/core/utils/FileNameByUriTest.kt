package org.queryquill.app.core.utils

import android.content.ContentProvider
import android.content.Context
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowContentResolver

@RunWith(RobolectricTestRunner::class)
class FileNameByUriTest {

    @get:Rule
    val tmp = TemporaryFolder()

    private val context: Context = ApplicationProvider.getApplicationContext()

    // ——— file:// URI ———

    @Test
    fun `file scheme returns just file name`() {
        val file = tmp.newFile("example.test.txt")
        val uri = Uri.fromFile(file)

        val name = fileNameByUri(context.contentResolver, uri)
        assertEquals("example.test.txt", name)
    }

    @Test
    fun `file scheme with empty path returns empty string`() {
        val uri = Uri.parse("file://")
        val name = fileNameByUri(context.contentResolver, uri)
        assertEquals("", name)
    }

    // ——— content:// URI ———

    private fun registerProvider(authority: String, displayName: String) {
        val provider = object : ContentProvider() {
            override fun onCreate() = true
            override fun query(
                uri: Uri, projection: Array<out String>?,
                selection: String?, selectionArgs: Array<out String>?,
                sortOrder: String?
            ): Cursor {
                val cursor = MatrixCursor(arrayOf(OpenableColumns.DISPLAY_NAME))
                cursor.addRow(arrayOf(displayName))
                return cursor
            }

            override fun getType(uri: Uri): String? = null
            override fun insert(uri: Uri, values: android.content.ContentValues?) = null
            override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0
            override fun update(
                uri: Uri, values: android.content.ContentValues?,
                selection: String?, selectionArgs: Array<out String>?
            ) = 0
        }
        ShadowContentResolver.registerProviderInternal(authority, provider)
    }

    @Test
    fun `content scheme returns DISPLAY_NAME`() {
        val auth = "test.prov"
        registerProvider(auth, "myphoto.jpeg")
        val uri = Uri.parse("content://$auth/some/path")

        val name = fileNameByUri(context.contentResolver, uri)
        assertEquals("myphoto.jpeg", name)
    }

    @Test
    fun `content scheme empty DISPLAY_NAME returns empty string`() {
        val auth = "test.prov2"
        registerProvider(auth, "")
        val uri = Uri.parse("content://$auth/whatever")

        val name = fileNameByUri(context.contentResolver, uri)
        assertEquals("", name)
    }

    @Test
    fun `unsupported scheme returns empty string`() {
        val uri = Uri.parse("ftp://server/file.txt")
        val name = fileNameByUri(context.contentResolver, uri)
        assertEquals("", name)
    }
}
