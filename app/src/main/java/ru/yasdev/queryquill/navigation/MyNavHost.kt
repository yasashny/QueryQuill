package ru.yasdev.queryquill.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.adaptive.ScreenState
import ru.yasdev.queryquill.adaptive.TopAppBarState
import ru.yasdev.queryquill.screens.httpRequestScreen.HttpRequestScreenViewModel
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreen
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreenViewModel
import ru.yasdev.queryquill.screens.mainScreen.MainScreen

@Composable
fun MyNavHost(navController: NavHostController, paddingValues: PaddingValues, screenState: ScreenState, mainActivityViewModel: MainActivityViewModel, httpResponseScreenViewModel: HttpResponseScreenViewModel, httpRequestScreenViewModel: HttpRequestScreenViewModel){
    NavHost(navController = navController, startDestination = MAIN_SCREEN) {
        composable(MAIN_SCREEN) { MainScreen(paddingValues = paddingValues, screenState, mainActivityViewModel, httpRequestScreenViewModel, httpResponseScreenViewModel) }
        composable(RESPONSE_SCREEN) { ResponseScreen(paddingValues = paddingValues, screenState, navController, mainActivityViewModel, httpResponseScreenViewModel) }
    }
}

const val MAIN_SCREEN = "MainScreen"
const val RESPONSE_SCREEN = "ResponseScreen"

@Composable
private fun ResponseScreen(paddingValues: PaddingValues, screenState: ScreenState, navController: NavController, mainActivityViewModel: MainActivityViewModel, httpResponseScreenViewModel: HttpResponseScreenViewModel){
    Surface(
        Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .fillMaxSize()
    ) {
        when(screenState){
            ScreenState.SINGLE_SCREEN -> {
                mainActivityViewModel.changeTopAppBarState(TopAppBarState.RESPONSE_SCREEN)
                HttpResponseScreen(modifier = Modifier.fillMaxSize().background(Color.Cyan), httpResponseScreenViewModel = httpResponseScreenViewModel)

            }
            else -> navController.popBackStack()
        }

    }
}