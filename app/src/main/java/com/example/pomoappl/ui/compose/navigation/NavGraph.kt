package com.example.pomoappl.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pomoappl.ui.compose.timer.TimerViewModel
import com.example.pomoappl.ui.compose.favorites.FavoritesScreen
import com.example.pomoappl.ui.compose.settings.SettingsScreen
import com.example.pomoappl.ui.compose.help.HelpScreen

@Composable
fun NavGraph(navController: Unit) {
    NavHost(
        navController = navController,
        startDestination = "pomodoro_timer"
    ) {
        composable("pomodoro_timer") {
            TimerViewModel()
        }
        composable("favorites") {
            FavoritesScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
        composable("help") {
            HelpScreen(navController = navController)
        }
    }
}

fun NavHost(
    navController: Any,
    startDestination: String,
    function: NavGraphBuilder.() -> Unit
) {
}
