package com.example.morningapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.morningapp.ui.screens.MorningScreen
import com.example.morningapp.ui.screens.NewSettingsScreen
import com.example.morningapp.ui.screens.SettingsEditScreen

@Composable
fun MorningNavigation(
    navController: NavHostController,
    isFirst: Boolean
) {
    val startRoute = if (isFirst) "NewSettings" else "Morning"

    NavHost(navController = navController, startDestination = startRoute) {
        composable("NewSettings") { NewSettingsScreen(navController = navController) }
        composable("SettingsEdit") { SettingsEditScreen(navController = navController) }
        composable("Morning") { MorningScreen(navController = navController) }
    }
}