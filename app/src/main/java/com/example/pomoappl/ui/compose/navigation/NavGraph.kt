// app/src/main/java/com/example/pomoappl/ui/compose/navigation/NavGraph.kt
package com.example.pomoappl.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomoappl.ui.compose.favorites.FavoritesScreen
import com.example.pomoappl.ui.compose.help.HelpScreen
import com.example.pomoappl.ui.compose.settings.SettingsScreen
import com.example.pomoappl.ui.compose.timer.TimerScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "pomodoro_timer"
    ) {
        composable("pomodoro_timer") { TimerScreen(navController = navController) }
        composable("favorites") { FavoritesScreen(navController = navController) }
        composable("settings") { SettingsScreen(navController = navController) }
        composable("help") { HelpScreen(navController = navController) }
    }
}