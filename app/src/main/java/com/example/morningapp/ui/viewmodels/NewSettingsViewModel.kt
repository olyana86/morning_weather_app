package com.example.morningapp.ui.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningapp.data.database.Settings
import com.example.morningapp.data.database.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun saveSettings(settings: Settings) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveSettings(settings)
        }
        sharedPreferences.edit().putString("token", "false").apply()
    }
}