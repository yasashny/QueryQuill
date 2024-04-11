package ru.yasdev.queryquill.activity

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.yasdev.queryquill.adaptive.adaptiveScreenManager
import ru.yasdev.queryquill.components.MyTopAppBar
import ru.yasdev.queryquill.navigationDrawer.NavigationDrawer
import ru.yasdev.queryquill.screens.mainScreen.MainScreen
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestViewModel
import ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreenViewModel
import ru.yasdev.queryquill.ui.theme.QueryQuillTheme

class MainActivity : ComponentActivity() {

    private lateinit var requestViewModel: RequestViewModel

    @OptIn(
        ExperimentalMaterial3WindowSizeClassApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            requestViewModel = koinViewModel<RequestViewModel>()
            val requestModel by requestViewModel.requestModel.collectAsState()
            val requestState by requestViewModel.requestState.collectAsState()
            QueryQuillTheme {
                NavigationDrawer(requestViewModel) { drawerState ->
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val screenState = adaptiveScreenManager(windowSizeClass)
                    val httpResponseScreenViewModel = koinViewModel<HttpResponseScreenViewModel>()
                    Scaffold(topBar = {
                        MyTopAppBar(
                            drawerState = drawerState,
                            label = requestModel.label,
                            updateRequest = requestViewModel::updateHttpRequest,
                            requestState = requestState
                        )
                    }) {
                        Surface(
                            Modifier
                                .padding(top = it.calculateTopPadding())
                                .fillMaxSize()
                        ) {
                            MainScreen(
                                screenState = screenState,
                                responseVM = httpResponseScreenViewModel,
                                requestModel = requestModel,
                                requestState = requestState,
                                updateRequest = requestViewModel::updateHttpRequest,
                                onEvent = requestViewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        requestViewModel.saveLastRequest()
    }
}