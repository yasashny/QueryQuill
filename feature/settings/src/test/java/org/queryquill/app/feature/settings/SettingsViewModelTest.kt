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

package org.queryquill.app.feature.settings

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.core.model.ThemeState
import org.queryquill.app.core.testing.repository.TestSettingsRepository
import org.queryquill.app.core.testing.util.MainDispatcherRule


class SettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val settingsRepository = TestSettingsRepository()

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(settingsRepository)
    }

    @Test
    fun `State is initially loading`() = runTest {
        assertEquals(SettingsUiState.Loading, viewModel.settingsUiState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `State is success after data loaded`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.settingsUiState.collect() }
        settingsRepository.changeSettings(SettingsModel(ThemeState.LIGHT))
        assertEquals(
            SettingsUiState.Success(SettingsModel(ThemeState.LIGHT)),
            viewModel.settingsUiState.value,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Update settings test`() = runTest(UnconfinedTestDispatcher()) {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.settingsUiState.collect() }
        viewModel.updateModel(UpdateSettings.UpdateTheme(ThemeState.LIGHT))
        assertEquals(
            SettingsUiState.Loading,
            viewModel.settingsUiState.value,
        )
        settingsRepository.changeSettings(SettingsModel(ThemeState.LIGHT))
        viewModel.updateModel(UpdateSettings.UpdateTheme(ThemeState.DARK))

        assertEquals(
            SettingsUiState.Success(SettingsModel(ThemeState.DARK)),
            viewModel.settingsUiState.value,
        )
    }
}