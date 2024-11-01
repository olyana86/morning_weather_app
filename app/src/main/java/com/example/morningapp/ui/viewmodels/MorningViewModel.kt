package com.example.morningapp.ui.viewmodels

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningapp.MyApplication
import com.example.morningapp.data.database.Settings
import com.example.morningapp.data.database.SettingsRepository
import com.example.morningapp.data.network.NetworkResponse
import com.example.morningapp.data.network.RetrofitInstance
import com.example.morningapp.data.network.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MorningViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val application: MyApplication
) : ViewModel() {

    val settings = settingsRepository.getSettings().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        Settings(1, "", "", "", 1)
    )

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult
    private var textToSpeech: TextToSpeech? = null
    private val isSpeaking = mutableStateOf(false)

    fun getData() {
        val location = "${settings.value.location} ${settings.value.country}"
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response =
                    weatherApi.getWeather("12358c608bf347f88cd114319242410", location, "ru")

                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Данные погоды, увы, не доступны.")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Не удалось загрузить данные")
            }
        }
    }

    fun convertTextToSpeech(txt: String) {
        isSpeaking.value = true
        if (textToSpeech == null) {
            textToSpeech = TextToSpeech(application.applicationContext) {
                if (it == TextToSpeech.SUCCESS) {
                    textToSpeech?.let { txtToSpeech ->
                        txtToSpeech.language = Locale.getDefault()
                        txtToSpeech.setSpeechRate(0.9f)
                        txtToSpeech.speak(
                            txt,
                            TextToSpeech.QUEUE_ADD,
                            null,
                            null
                        )
                    }
                }
                isSpeaking.value = false
            }
        }
    }
}