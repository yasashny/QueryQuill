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

package org.queryquill.app.feature.transaction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun PrimaryTextTabs(tabsScreenState: MutableState<TabsScreenState>) {
    val titles = listOf(stringResource(R.string.request), stringResource(R.string.response))
    Column {
        PrimaryTabRow(
            selectedTabIndex = tabsScreenState.value.pageIndex
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.testTag(title),
                    selected = tabsScreenState.value.pageIndex == index,
                    onClick = {
                        tabsScreenState.value = selectPage(index)
                    },
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    icon = {
                        if (index == 0) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_north_24),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_south_24),
                                contentDescription = null
                            )
                        }
                    })
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
private fun PreviewPrimaryTextTabs() {
    PrimaryTextTabs(mutableStateOf(TabsScreenState.REQUEST))
}