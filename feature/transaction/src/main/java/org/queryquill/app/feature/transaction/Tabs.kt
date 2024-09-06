package org.queryquill.app.feature.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun PrimaryTextTabs(tabsScreenState: MutableState<TabsScreenState>) {
    val titles = listOf(stringResource(R.string.request), stringResource(R.string.response))
    Column {
        PrimaryTabRow(
            selectedTabIndex = tabsScreenState.value.pageIndex,
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ) {
            titles.forEachIndexed { index, title ->
                Tab(selected = tabsScreenState.value.pageIndex == index,
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