// app/src/main/java/com/example/pomoappl/MainActivity.kt
package com.example.pomoappl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomoappl.ui.compose.navigation.SetupNavGraph
import com.example.pomoappl.ui.theme.PomoAppLTheme
import com.example.pomoappl.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val isDarkModeEnabled by settingsViewModel.isDarkModeEnabled.collectAsState()

            PomoAppLTheme(darkTheme = isDarkModeEnabled) { // Aplica o tema com base na configuração
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}

private fun MainActivity.rememberNavController() {
    TODO("Not yet implemented")
}
