package com.example.morningapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.morningapp.data.network.NetworkResponse
import com.example.morningapp.data.network.WeatherModel
import com.example.morningapp.ui.models.getAdvice
import com.example.morningapp.ui.models.getDayWish
import com.example.morningapp.ui.viewmodels.MorningViewModel
import com.example.morningapp.utils.getDateTodayForMorningScreen
import com.example.morningapp.utils.getDayOfWeekForMorningScreen

@Composable
fun MorningScreen(
    navController: NavHostController
) {
    val viewModel: MorningViewModel = hiltViewModel()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val weatherResult = viewModel.weatherResult.observeAsState()
    val date = getDateTodayForMorningScreen()
    val dayOfWeek = getDayOfWeekForMorningScreen()
    val advice = getAdvice()
    val dayWish = getDayWish()

    LaunchedEffect(key1 = settings.location.isNotEmpty()) {
        viewModel.getData()
    }

    if (settings.name != "") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Доброе утро,",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${settings.name.trim()}!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Сегодня $date, $dayOfWeek.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    when (val result = weatherResult.value) {
                        is NetworkResponse.Error -> {
                            Text(
                                text = result.message,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        NetworkResponse.Loading -> {
                            CircularProgressIndicator()
                        }

                        is NetworkResponse.Success -> {
                            WeatherDetails(data = result.data)
                            if (settings.tTSIsNeeded == 1) {
                                val txt =
                                    "Доброе утро, ${settings.name}. Сегодня $date, $dayOfWeek." +
                                            "За окном сейчас ${result.data.current.temp_c} по Цельсию" +
                                            result.data.current.condition.text +
                                            "Скорость ветра - ${result.data.current.wind_kph} км/ч, " +
                                            "влажность воздуха - ${result.data.current.humidity}%." +
                                            "Твой совет на сегодня: ${advice.text}. ${settings.name}, ${dayWish.text}"
                                viewModel.convertTextToSpeech(txt)
                            }
                        }

                        null -> {}
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Твой совет на сегодня: ${advice.text}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${settings.name.trim()}!",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dayWish.text,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { navController.navigate("SettingsEdit") }) {
                            Icon(
                                Icons.Default.Settings,
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.CenterVertically),
                                contentDescription = "AllHabits",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherModel) {
    val temperature = "${data.current.temp_c}° c"
    val humidity = "${data.current.humidity}%"
    val wind = "${data.current.wind_kph} км/ч"
    val condition = data.current.condition.text

    Row(Modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically),
            model = "https:${data.current.condition.icon}",
            contentDescription = "Condition icon"
        )
        Text(
            text = "За окном сейчас $temperature. $condition. Скорость ветра - $wind, влажность воздуха - $humidity.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
