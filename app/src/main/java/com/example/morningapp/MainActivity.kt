package com.example.morningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.morningapp.navigation.MorningNavigation
import com.example.morningapp.ui.theme.MorningTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorningTheme {
                val navController = rememberNavController()
                val sharedPrefs = getSharedPreferences("PREFS_NAME", MODE_PRIVATE)
                val token = sharedPrefs.getString("token", "true")
                val isFirst = token.equals("true")

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    MorningNavigation(navController = navController, isFirst = isFirst)
                }
            }
        }
    }
}

