package ru.yasdev.queryquill.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import org.koin.androidx.compose.koinViewModel
import ru.yasdev.queryquill.components.MyTopAppBar
import ru.yasdev.queryquill.ui.theme.QueryQuillTheme
import ru.yasdev.queryquill.adaptive.adaptiveScreenManager
import ru.yasdev.queryquill.navigationDrawer.NavigationDrawer
import ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreenViewModel
import ru.yasdev.queryquill.screens.mainScreen.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainActivityViewModel = koinViewModel<MainActivityViewModel>()
            QueryQuillTheme {
                NavigationDrawer(mainActivityViewModel) { drawerState ->
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    val screenState = adaptiveScreenManager(windowSizeClass)

                    val httpResponseScreenViewModel = koinViewModel<HttpResponseScreenViewModel>()
                    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            MyTopAppBar(
                                scrollBehavior = scrollBehavior, drawerState = drawerState
                            )
                        }) {
                        Surface(
                            Modifier
                                .padding(top = it.calculateTopPadding())
                                .fillMaxSize()
                        ) {
                            MainScreen(
                                screenState = screenState,
                                viewModel = mainActivityViewModel,
                                responseVM = httpResponseScreenViewModel
                            )

                        }
                    }
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        mainActivityViewModel.save()
    }
}