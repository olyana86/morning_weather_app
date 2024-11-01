package com.example.morningapp.data.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val settingsDao: SettingsDao) {

    fun getSettings(): Flow<Settings> = settingsDao.getSettings()
    suspend fun saveSettings(settings: Settings) = settingsDao.saveSettings(settings)
    suspend fun updateSettings(settings: Settings) = settingsDao.updateSettings(settings)
}