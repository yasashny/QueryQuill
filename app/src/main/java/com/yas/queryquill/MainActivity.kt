package com.yas.queryquill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.yas.model.ThemeState
import com.yas.queryquill.ui.theme.QueryQuillTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {

            val navController = rememberNavController()

            val vm = koinViewModel<MainViewModel>()
            val theme = vm.themeState.collectAsState().value

            splashScreen.setKeepOnScreenCondition { theme == null }

            TextMateInit(applicationContext, theme ?: ThemeState.SYSTEM)
            val windowSizeClass = calculateWindowSizeClass(this)
            val screenState = adaptiveScreenManager(windowSizeClass)

            QueryQuillTheme(theme = theme ?: ThemeState.SYSTEM) {

                Navigation(navController, screenState)
            }
        }
    }
}