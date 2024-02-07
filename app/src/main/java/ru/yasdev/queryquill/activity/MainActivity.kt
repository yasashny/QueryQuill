package ru.yasdev.queryquill.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.yasdev.queryquill.components.MyTopAppBar
import ru.yasdev.queryquill.ui.theme.QueryQuillTheme
import ru.yasdev.queryquill.adaptive.adaptiveScreenManager
import ru.yasdev.queryquill.navigation.MyNavHost
import ru.yasdev.queryquill.navigationDrawer.NavigationDrawer

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QueryQuillTheme {
                NavigationDrawer {
                    val drawerState = it
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val navController = rememberNavController()
                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                    val screenState = adaptiveScreenManager(windowSizeClass)
                    val mainActivityViewModel by viewModels<MainActivityViewModel>()
                    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            MyTopAppBar(scrollBehavior = scrollBehavior, navController = navController, mainActivityViewModel, drawerState = drawerState)
                        }) {

                        MyNavHost(navController = navController, paddingValues = it, screenState = screenState, mainActivityViewModel)
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QueryQuillTheme {}
}