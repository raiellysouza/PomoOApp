// app/src/main/java/com/example/pomoappl/ui/compose/help/HelpScreen.kt
package com.example.pomoappl.ui.compose.help

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajuda e Suporte") },
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
                            text = { Text("Configurações") },
                            onClick = { navController.navigate("settings"); expanded = false }
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
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Configurações") },
                    label = { Text("Configurações") }
                )
            }
        }
    ) { paddingValues ->
        val faqs = remember {
            listOf(
                "O que é a técnica Pomodoro?",
                "Como funciona o cronômetro?",
                "Posso personalizar os tempos?",
                "Onde salvo minhas configurações favoritas?",
                "Como ativar/desativar notificações?"
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text("Perguntas Frequentes (FAQs)", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(faqs) { faq ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = faq, style = MaterialTheme.typography.titleMedium)
                        // Você pode adicionar um expander para a resposta aqui
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Resposta para: '$faq'", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { /* Simular envio de mensagem */ }) {
                    Text("Enviar Mensagem para Suporte")
                }
            }
        }
    }
}