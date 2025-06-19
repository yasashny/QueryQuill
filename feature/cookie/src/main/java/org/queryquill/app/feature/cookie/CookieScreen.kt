/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.cookie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.ui.QueryQuillTopBar
import org.queryquill.app.core.ui.SaveDataOnStop
import org.queryquill.app.feature.cookie.util.TestTags

@Composable
fun CookieScreen(navigateUp: () -> Unit) {
    val vm = koinViewModel<CookieViewModel>()
    val cookieUiState = vm.cookieState.collectAsStateWithLifecycle().value
    CookieScreen(cookieUiState, vm::onEvent, navigateUp, vm::saveCookie)
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun CookieScreen(
    uiState: CookieUiState,
    onEvent: (UpdateCookie) -> Unit,
    navigateUp: () -> Unit,
    saveCookieOnStop: () -> Unit
) {
    Scaffold(topBar = {
        QueryQuillTopBar(title = {
            Text(
                text = stringResource(R.string.cookie)
            )
        }, navigationIcon = {
            TextButton(onClick = {
                navigateUp()
            }, modifier = Modifier.testTag(TestTags.CookieScreen.NAVIGATE_UP_BUTTON)) {
                Text(text = stringResource(R.string.done))
            }
        }, actions = {
            IconButton(
                onClick = {
                    onEvent(UpdateCookie.Add)
                }, modifier = Modifier.testTag(TestTags.CookieScreen.ADD_COOKIE_BUTTON)
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        })
    }) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
            ) {
                when (uiState) {
                    CookieUiState.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingIndicator(
                                modifier = Modifier.testTag(TestTags.CookieScreen.LOADING_INDICATOR)
                            )
                        }
                    }

                    is CookieUiState.Success -> {
                        LazyColumn(modifier = Modifier.widthIn(max = 1000.dp)) {
                            item("info_message") {
                                InfoMessage()
                            }
                            itemsIndexed(
                                uiState.list, key = { _, item -> item.id }) { index, item ->
                                CookieListItem(
                                    modifier = Modifier
                                        .padding(
                                            start = 15.dp, top = 15.dp, end = 15.dp
                                        )
                                        .animateItem(), item, index, onEvent
                                )
                            }
                            item("spacer") {
                                Spacer(Modifier.padding(50.dp))
                            }
                        }
                    }
                }
                SaveDataOnStop { saveCookieOnStop() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CookieScreenPreview() {
    CookieScreen(
        uiState = CookieUiState.Success(
            listOf(
                CookieModel("cookie1"),
                CookieModel("cookie2"),
                CookieModel("cookie3"),
                CookieModel("cookie4"),
                CookieModel("cookie5"),
            )
        ), onEvent = {}, navigateUp = {}, saveCookieOnStop = {})
}

@Preview(showBackground = true)
@Composable
private fun CookieScreenLoadingPreview() {
    CookieScreen(
        uiState = CookieUiState.Loading,
        onEvent = {},
        navigateUp = {},
        saveCookieOnStop = {})
}




