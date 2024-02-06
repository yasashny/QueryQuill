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
import ru.yasdev.queryquill.adaptive.ScreenState
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreen
import ru.yasdev.queryquill.screens.mainScreen.MainScreen

@Composable
fun MyNavHost(navController: NavHostController, paddingValues: PaddingValues, screenState: ScreenState){
    NavHost(navController = navController, startDestination = MAIN_SCREEN) {
        composable(MAIN_SCREEN) { MainScreen(paddingValues = paddingValues, screenState) }
        composable(RESPONSE_SCREEN) { ResponseScreen(paddingValues = paddingValues, screenState, navController) }
    }
}

const val MAIN_SCREEN = "MainScreen"
const val RESPONSE_SCREEN = "ResponseScreen"

@Composable
private fun ResponseScreen(paddingValues: PaddingValues, screenState: ScreenState, navController: NavController){
    Surface(
        Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .fillMaxSize()
    ) {
        when(screenState){
            ScreenState.SINGLE_SCREEN -> {
                HttpResponseScreen(modifier = Modifier.fillMaxSize().background(Color.Cyan))

            }
            else -> navController.popBackStack()
        }

    }
}