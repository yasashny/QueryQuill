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

package org.queryquill.app.core.datastore

import androidx.datastore.preferences.core.mutablePreferencesOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.core.model.ThemeState

class SettingsDataSourceTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: SettingsDataSource

    @Before
    fun setup() {
        subject = SettingsDataSource(InMemoryDataStore(mutablePreferencesOf()))
    }

    @Test
    fun testDefaultSettings() = testScope.runTest {
        val settings = subject.getSettings().first()
        assertEquals(ThemeState.SYSTEM, settings.themeState)
    }

    @Test
    fun testUpdateSettingsToDark() = testScope.runTest {
        subject.updateSettings(SettingsModel(ThemeState.DARK))
        val settings = subject.getSettings().first()
        assertEquals(ThemeState.DARK, settings.themeState)
    }

    @Test
    fun testUpdateSettingsToLight() = testScope.runTest {
        subject.updateSettings(SettingsModel(ThemeState.LIGHT))
        val settings = subject.getSettings().first()
        assertEquals(ThemeState.LIGHT, settings.themeState)
    }

    @Test
    fun testUpdateSettingsToSystem() = testScope.runTest {
        subject.updateSettings(SettingsModel(ThemeState.SYSTEM))
        val settings = subject.getSettings().first()
        assertEquals(ThemeState.SYSTEM, settings.themeState)
    }
}