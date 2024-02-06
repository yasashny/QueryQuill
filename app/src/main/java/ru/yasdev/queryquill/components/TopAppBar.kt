package ru.yasdev.queryquill.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.adaptive.TopAppBarState

import ru.yasdev.queryquill.navigation.RESPONSE_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, mainActivityViewModel: MainActivityViewModel) {
    val topAppBarState by mainActivityViewModel.topAppBarState.collectAsState()
    CenterAlignedTopAppBar(
        title = {
            Text(
                "TopAppBar",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (topAppBarState == TopAppBarState.RESPONSE_SCREEN){
                IconButton(onClick = { navController.popBackStack()}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
            else{
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }




        },
        actions = {
            if (topAppBarState == TopAppBarState.SINGLE_SCREEN){
                Button(onClick = { navController.navigate(RESPONSE_SCREEN) }) {
                    Text(text = "Response")
                }
            }




        },
        scrollBehavior = scrollBehavior
    )
}