package com.example.pomoappl.ui.compose.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomoappl.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: Unit, viewModel: SettingsViewModel = viewModel()) {
    val isDarkModeEnabled by viewModel.isDarkModeEnabled.collectAsState()
    val areNotificationsEnabled by viewModel.areNotificationsEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
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
                            text = { Text("Pomodoro") },
                            onClick = { navController.navigate("pomodoro_timer"); expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = { navController.navigate("favorites"); expanded = false }
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
                    selected = false,
                    onClick = { navController.navigate("pomodoro_timer") },
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
                    selected = true,
                    onClick = { },
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
                .padding(16.dp)
        ) {
            // Switch para Modo Escuro
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Modo Escuro")
                Switch(
                    checked = isDarkModeEnabled,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notificações")
                Switch(
                    checked = areNotificationsEnabled,
                    onCheckedChange = { viewModel.toggleNotifications() }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Botões de ação
            Button(
                onClick = { /* TODO: Implementar lógica para limpar favoritos */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpar Favoritos")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* TODO: Implementar lógica para redefinir preferências */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Redefinir Preferências")
            }
        }
    }
}

private fun Unit.navigate(string: String) {}
