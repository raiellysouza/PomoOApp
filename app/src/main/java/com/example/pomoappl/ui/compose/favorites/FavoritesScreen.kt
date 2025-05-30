package com.example.pomoappl.ui.compose.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.icons.filled.Timer // Importação correta para Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController // Importação correta para NavController
import com.example.pomoappl.data.model.PomodoroConfig
import com.example.pomoappl.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, viewModel: FavoritesViewModel = viewModel()) {
    val favoriteConfigs by viewModel.favoriteConfigs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações Favoritas") },
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
                            onClick = { navController.navigate("pomodoro_timer"); expanded = false } // Use navController.navigate
                        )
                        DropdownMenuItem(
                            text = { Text("Configurações") },
                            onClick = { navController.navigate("settings"); expanded = false } // Use navController.navigate
                        )
                        DropdownMenuItem(
                            text = { Text("Ajuda") },
                            onClick = { navController.navigate("help"); expanded = false } // Use navController.navigate
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("pomodoro_timer") }, // Use navController.navigate
                    icon = { Icon(Icons.Default.Timer, contentDescription = "Pomodoro") },
                    label = { Text("Pomodoro") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Já estamos na tela de favoritos */ },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") }, // Use navController.navigate
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Configurações") },
                    label = { Text("Configurações") }
                )
            }
        }
    ) { paddingValues ->
        if (favoriteConfigs.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nenhuma configuração favorita ainda.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(8.dp)
            ) {
                items(favoriteConfigs) { config ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = config.nome, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Foco: ${config.duracaoFocoMinutos} min, Pausa Curta: ${config.duracaoPausaCurtaMinutos} min",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Pausa Longa: ${config.duracaoPausaLongaMinutos} min, Ciclos: ${config.ciclosAtePausaLonga}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.removeFavoriteConfig(config) }) {
                                Text("Remover dos Favoritos")
                            }
                        }
                    }
                }
            }
        }
    }
}