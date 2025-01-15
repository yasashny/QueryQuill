package org.queryquill.app.core.data

import kotlinx.coroutines.flow.Flow
import org.queryquill.app.core.model.SettingsModel

interface SettingsRepository {
    fun getSettings(): Flow<SettingsModel>
    suspend fun changeSettings(model: SettingsModel)
}