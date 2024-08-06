package com.yas.queryquill.components.topAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.yas.model.BodyState
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorTopAppBarState(
    requestStateFlow: StateFlow<RequestState>, navController: NavHostController
) {
    val requestModel = requestStateFlow.collectAsState().value as RequestState.Request
    val textType = (requestModel.request.bodyState as BodyState.Text).textType
    TopAppBar(title = {
        Text(text = "Text/${textType.title}")

    }, navigationIcon = {
        TextButton(onClick = { navController.navigateUp() }) {
            Text(text = "Done")
        }
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ))
}