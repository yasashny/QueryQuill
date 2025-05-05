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

package org.queryquill.app

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import org.queryquill.app.core.model.ScreenState

fun adaptiveScreenManager(windowSizeClass: WindowSizeClass): ScreenState {
    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        return ScreenState.SINGLE_SCREEN
    } else {
        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium) {
            return if (windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded) {
                ScreenState.COLUMN_SCREEN
            } else {
                ScreenState.SINGLE_SCREEN
            }
        } else {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                return ScreenState.ROW_SCREEN
            }
        }
    }
    return ScreenState.SINGLE_SCREEN
}