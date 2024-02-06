package ru.yasdev.queryquill.adaptive

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun adaptiveScreenManager(windowSizeClass: WindowSizeClass): ScreenState {
    if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
        return ScreenState.SINGLE_SCREEN
    } else {
        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium) {
            return if(windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded) {
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