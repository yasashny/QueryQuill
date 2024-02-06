package ru.yasdev.queryquill.screens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.yasdev.queryquill.screens.httpRequestScreen.HttpRequestScreen
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreen
import ru.yasdev.queryquill.adaptive.ScreenState

@Composable
fun MainScreen(paddingValues: PaddingValues, screenState: ScreenState) {
    Surface(
        Modifier
            .padding(top = paddingValues.calculateTopPadding())
            .fillMaxSize()
    ) {

        when(screenState){
            ScreenState.SINGLE_SCREEN ->{
                HttpRequestScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Blue)
                )
            }
            ScreenState.ROW_SCREEN -> {
                Row {

                    HttpRequestScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(Color.Blue)
                    )

                    HttpResponseScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(Color.Red)
                    )
                }
            }
            ScreenState.COLUMN_SCREEN -> {
                Column {

                    HttpRequestScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(Color.Blue)
                    )


                    HttpResponseScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(Color.Red)
                    )
                }
            }
        }
    }


}