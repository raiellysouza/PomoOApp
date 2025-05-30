// app/src/main/java/com/example/pomoappl/ui/compose/timer/TimerScreen.kt
package com.example.pomoappl.ui.compose.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pomoappl.viewmodel.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(navController: NavController, viewModel: TimerViewModel = viewModel()) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val currentCycleType by viewModel.currentCycleType.collectAsState()
    val focusCyclesCompleted by viewModel.focusCyclesCompleted.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pomodoro App") },
                actions = {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = { navController.navigate("favorites"); expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Configurações") },
                            onClick = { navController.navigate("settings"); expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Ajuda") },
                            onClick = { navController.navigate("help"); expanded = false }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Já estamos na tela do timer */ },
                    icon = { Icon(Icons.Default.Timer, contentDescription = "Pomodoro") },
                    label = { Text("Pomodoro") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("favorites") },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Configurações") },
                    label = { Text("Configurações") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Exibe o tipo de ciclo atual
            Text(
                text = when (currentCycleType) {
                    TimerViewModel.CycleType.FOCUS -> "Tempo de Foco"
                    TimerViewModel.CycleType.SHORT_BREAK -> "Pausa Curta"
                    TimerViewModel.CycleType.LONG_BREAK -> "Pausa Longa"
                },
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Exibe o tempo restante
            Text(
                text = "${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
                style = MaterialTheme.typography.displayLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botões de controle do cronômetro
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { viewModel.startTimer() }, enabled = !isRunning) {
                    Text("Iniciar")
                }
                Button(onClick = { viewModel.pauseTimer() }, enabled = isRunning) {
                    Text("Pausar")
                }
                Button(onClick = { viewModel.resetTimer() }) {
                    Text("Resetar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Exibe o número de ciclos de foco concluídos
            Text(
                text = "Ciclos de Foco Concluídos: $focusCyclesCompleted",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}