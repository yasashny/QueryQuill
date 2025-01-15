package org.queryquill.app.core.testing.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.queryquill.app.core.data.SettingsRepository
import org.queryquill.app.core.model.SettingsModel

class TestSettingsRepository : SettingsRepository {

    private val settingsFlow = MutableSharedFlow<SettingsModel>(replay = 1)

    override fun getSettings(): Flow<SettingsModel> = settingsFlow.asSharedFlow()

    override suspend fun changeSettings(model: SettingsModel) {
        settingsFlow.tryEmit(model)
    }
}