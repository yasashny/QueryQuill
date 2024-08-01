package com.yas.queryquill.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.yas.common.ThemeState
import com.yas.queryquill.adaptive.adaptiveScreenManager
import com.yas.queryquill.components.topAppBar.MyTopAppBar
import com.yas.queryquill.navigation.Destinations
import com.yas.queryquill.navigation.Navigation
import com.yas.queryquill.navigationDrawer.NavigationDrawer
import com.yas.queryquill.screens.requestScreens.viewModel.RequestViewModel
import com.yas.queryquill.ui.theme.QueryQuillTheme
import com.yas.queryquill.utils.TextMateInit
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private lateinit var requestViewModel: RequestViewModel

    @OptIn(
        ExperimentalMaterial3WindowSizeClassApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            val vm = koinViewModel<MainViewModel>()
            val theme = vm.themeState.collectAsState().value
            splashScreen.setKeepOnScreenCondition {
                theme == null
            }
            requestViewModel = koinViewModel<RequestViewModel>()
            val navController = rememberNavController()
            TextMateInit(applicationContext, theme ?: ThemeState.SYSTEM)
            QueryQuillTheme(theme = theme ?: ThemeState.SYSTEM) {
                NavigationDrawer(requestViewModel,
                    navigateToSettings = { navController.navigate(Destinations.SettingsScreenRoute.route) }) { drawerState ->
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val screenState = adaptiveScreenManager(windowSizeClass)
                    Scaffold(topBar = {
                        MyTopAppBar(
                            drawerState = drawerState,
                            requestModelFlow = requestViewModel.requestModel,
                            updateRequest = requestViewModel::updateHttpRequest,
                            requestStateFlow = requestViewModel.requestState,
                            navController = navController
                        )
                    }) {
                        Surface(
                            Modifier
                                .padding(it)
                                .fillMaxSize()
                        ) {
                            Navigation(
                                navController = navController,
                                screenState = screenState,
                                requestModel = requestViewModel.requestModel,
                                requestState = requestViewModel.requestState,
                                updateRequest = requestViewModel::updateHttpRequest,
                                sendRequest = requestViewModel::sendRequest,
                                onEvent = requestViewModel::onEvent,
                                responseState = requestViewModel.responseState
                            )
                        }
                    }
                }
            }

        }
    }

    override fun onStop() {
        requestViewModel.saveLastRequest()
        super.onStop()
    }
}