package com.example.morningapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.morningapp.data.database.Settings
import com.example.morningapp.ui.components.TTSStatusChooser
import com.example.morningapp.ui.viewmodels.NewSettingsViewModel
import com.example.morningapp.utils.booleanToInt

@Composable
fun NewSettingsScreen(navController: NavHostController) {
    val viewModel: NewSettingsViewModel = hiltViewModel()

    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val tTSIsNeeded = remember { mutableStateOf(true) }

    val currentSettings = Settings(
        id = 1,
        name = name.value,
        location = location.value,
        country = country.value,
        tTSIsNeeded = booleanToInt(tTSIsNeeded.value)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Настройки приветствия",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name.value,
            onValueChange = {
                name.value = it
            },
            label = { Text(text = "Как тебя назвать утром?") },
            singleLine = true,
            supportingText = { Text(text = "пример: Солнышко, Красавчик, Анюта, пр.") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Твое местоположение (для погоды)")
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.weight(0.6f),
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text(text = "Город/село") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                modifier = Modifier.weight(0.4f),
                value = country.value,
                onValueChange = { country.value = it },
                label = { Text(text = "Страна") },
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TTSStatusChooser(
                isChecked = tTSIsNeeded.value,
                onCheckedChange = { tTSIsNeeded.value = it }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = {
                    if (name.value == "" || location.value == "" || country.value == "") {
                        Toast.makeText(context, "Не все поля заполнены...", Toast.LENGTH_LONG).show()
                    } else {
                        viewModel.saveSettings(currentSettings)
                        navController.navigate("Morning")
                    }
                }
            ) {
                Text(text = "Сохранить")
            }
        }
    }
}