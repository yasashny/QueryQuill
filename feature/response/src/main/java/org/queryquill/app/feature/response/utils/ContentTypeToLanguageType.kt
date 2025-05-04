package org.queryquill.app.feature.response.utils

import android.os.Build
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.LanguageType


/**
 * Converts a given ContentType to its corresponding LanguageType based on platform API level compatibility.
 *
 * @param contentType The type of content (e.g., text, image, application) to be converted to a LanguageType.
 * @return The corresponding LanguageType for the provided ContentType. If the ContentType
 * is not supported or compatible with the platform's API level, it returns LanguageType.OTHER.
 */
internal fun contentTypeToLanguageType(contentType: ContentType): LanguageType {
    return when (contentType) {
        ContentType.Text.HTML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.HTML
        } else {
            LanguageType.OTHER
        }

        ContentType.Image.JPEG -> LanguageType.OTHER
        ContentType.Application.JSON -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.JSON
        } else {
            LanguageType.OTHER
        }

        ContentType.Text.PLAIN -> LanguageType.PLAIN
        ContentType.Image.PNG -> LanguageType.OTHER
        ContentType.Image.WEBP -> LanguageType.OTHER
        ContentType.Text.XML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.XML
        } else {
            LanguageType.OTHER
        }

        ContentType.Image.BMP -> LanguageType.OTHER
        ContentType.Image.HEIC -> LanguageType.OTHER
        ContentType.Image.HEIF -> LanguageType.OTHER
    }
}