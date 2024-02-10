package ru.yasdev.queryquill.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch
import ru.yasdev.queryquill.activity.MainActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(scrollBehavior: TopAppBarScrollBehavior, drawerState: DrawerState) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "TopAppBar", maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {

            val scope = rememberCoroutineScope()

            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Localized description")
            }


        },
        actions = {},
        //scrollBehavior = scrollBehavior
    )
}