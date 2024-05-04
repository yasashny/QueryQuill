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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import com.yas.queryquill.adaptive.adaptiveScreenManager
import com.yas.queryquill.components.MyTopAppBar
import com.yas.queryquill.navigationDrawer.NavigationDrawer
import com.yas.queryquill.screens.mainScreen.MainScreen
import com.yas.queryquill.screens.requestScreens.viewModel.RequestViewModel
import com.yas.queryquill.ui.theme.QueryQuillTheme

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
            val responseModel by requestViewModel.responseState.collectAsState()
            QueryQuillTheme {
                NavigationDrawer(requestViewModel) { drawerState ->
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val screenState = adaptiveScreenManager(windowSizeClass)
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
                                requestModel = requestModel,
                                requestState = requestState,
                                updateRequest = requestViewModel::updateHttpRequest,
                                onEvent = requestViewModel::onEvent,
                                sendRequest = requestViewModel::sendRequest,
                                responseState = responseModel

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