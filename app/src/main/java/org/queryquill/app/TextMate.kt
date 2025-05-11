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

package org.queryquill.app

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import org.eclipse.tm4e.core.registry.IThemeSource
import org.queryquill.app.core.model.ThemeState

@Composable
fun TextMateInit(applicationContext: Context, theme: ThemeState) {
    FileProviderRegistry.getInstance().addFileProvider(
        AssetsFileResolver(
            applicationContext.assets
        )
    )
    val themeRegistry = ThemeRegistry.getInstance()
    val isDarkTheme = when (theme) {
        ThemeState.SYSTEM -> isSystemInDarkTheme()
        ThemeState.DARK -> true
        ThemeState.LIGHT -> false
    }
    val name = if (isDarkTheme) {
        "darcula"
    } else {
        "quietlight"
    }
    val themeAssetsPath = "textmate/$name.json"
    themeRegistry.loadTheme(
        ThemeModel(
            IThemeSource.fromInputStream(
                FileProviderRegistry.getInstance().tryGetInputStream(themeAssetsPath),
                themeAssetsPath,
                null
            ), name
        ).apply {
            isDark = isDarkTheme
        })
    ThemeRegistry.getInstance().setTheme(name)
    GrammarRegistry.getInstance().loadGrammars("textmate/languages.json")

}