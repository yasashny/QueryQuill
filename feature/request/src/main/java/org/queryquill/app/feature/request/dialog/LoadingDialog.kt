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

package org.queryquill.app.feature.request.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun LoadingDialog(
    onDismiss: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = {
        onDismiss()
    }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(Dimens.medium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingIndicator(
                    modifier = Modifier
                        .padding(Dimens.medium)
                        .testTag(TestTags.LoadingDialog.LOADING_INDICATOR)
                )
                TextButton(
                    modifier = Modifier.testTag(TestTags.LoadingDialog.CANCEL_BUTTON), onClick = {
                        onDismiss()
                    }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewLoadingAlertDialog() {
    QueryQuillTheme(dynamicColor = false) {
        LoadingDialog { }
    }
}