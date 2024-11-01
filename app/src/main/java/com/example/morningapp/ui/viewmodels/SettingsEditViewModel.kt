package com.example.morningapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningapp.data.database.Settings
import com.example.morningapp.data.database.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsEditViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val settings = settingsRepository.getSettings().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        Settings(1, "", "", "", 1)
    )

    fun updateSettings(settings: Settings) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.updateSettings(settings)
        }
    }
}