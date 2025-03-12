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