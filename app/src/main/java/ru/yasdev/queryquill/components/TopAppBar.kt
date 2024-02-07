package ru.yasdev.queryquill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.yasdev.queryquill.R
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.adaptive.TopAppBarState

import ru.yasdev.queryquill.navigation.RESPONSE_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, mainActivityViewModel: MainActivityViewModel, drawerState: DrawerState) {
    val topAppBarState by mainActivityViewModel.topAppBarState.collectAsState()
    TopAppBar(
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
                val scope = rememberCoroutineScope()
                IconButton(onClick = { /* doSomething() */ }) {
                    IconButton(onClick = {scope.launch { drawerState.open() }}){
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Localized description")
                    }

                }
            }




        },
        actions = {
            if (topAppBarState == TopAppBarState.SINGLE_SCREEN){
                AssistChip(modifier = Modifier.padding(end = 5.dp), onClick = { navController.navigate(RESPONSE_SCREEN) }, label = { Text(
                    text = "Response", fontSize = 16.sp
                ) },
                    leadingIcon = {Icon(Icons.Rounded.ExitToApp, contentDescription = null, Modifier.size(AssistChipDefaults.IconSize))})
            }





        },
        scrollBehavior = scrollBehavior
    )
}