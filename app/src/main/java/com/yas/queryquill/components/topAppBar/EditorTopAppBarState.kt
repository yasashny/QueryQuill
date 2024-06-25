package com.yas.queryquill.components.topAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.requestsDb.states.BodyState
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorTopAppBarState(
    requestModelFlow: StateFlow<RequestModel>, navController: NavHostController
) {
    val requestModel by requestModelFlow.collectAsState()
    val textType = (requestModel.bodyState as BodyState.Text).textType
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