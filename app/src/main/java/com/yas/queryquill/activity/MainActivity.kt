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
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yas.queryquill.adaptive.adaptiveScreenManager
import com.yas.queryquill.components.topAppBar.MyTopAppBar
import com.yas.queryquill.navigation.Navigation
import com.yas.queryquill.navigationDrawer.NavigationDrawer
import com.yas.queryquill.screens.requestScreens.viewModel.RequestViewModel
import com.yas.queryquill.ui.theme.QueryQuillTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private lateinit var requestViewModel: RequestViewModel

    @OptIn(
        ExperimentalMaterial3WindowSizeClassApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            requestViewModel = koinViewModel<RequestViewModel>()
            val navController = rememberNavController()
            QueryQuillTheme {
                NavigationDrawer(requestViewModel) { drawerState ->
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