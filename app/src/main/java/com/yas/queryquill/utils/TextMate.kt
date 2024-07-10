package com.yas.queryquill.utils

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import org.eclipse.tm4e.core.registry.IThemeSource

@Composable
fun TextMateInit(applicationContext: Context) {
    FileProviderRegistry.getInstance().addFileProvider(
        AssetsFileResolver(
            applicationContext.assets
        )
    )
    val themeRegistry = ThemeRegistry.getInstance()
    val name = if (isSystemInDarkTheme()) {
        "darcula"
    } else {
        "quietlight"
    }
    val themeAssetsPath = "textmate/$name.json"
    themeRegistry.loadTheme(ThemeModel(
        IThemeSource.fromInputStream(
            FileProviderRegistry.getInstance().tryGetInputStream(themeAssetsPath),
            themeAssetsPath,
            null
        ), name
    ).apply {
        isDark = isSystemInDarkTheme()
    })
    ThemeRegistry.getInstance().setTheme(name)
    GrammarRegistry.getInstance().loadGrammars("textmate/languages.json")

}