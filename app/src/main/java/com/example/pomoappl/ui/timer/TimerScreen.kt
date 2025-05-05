package com.example.pomoappl.ui.timer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimerScreen() {
    var timeLeft by remember { mutableStateOf(1500)
    var isRunning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isRunning) {
                isRunning = true
                LaunchedEffect(Unit) {
                    while (timeLeft > 0 && isRunning) {
                        kotlinx.coroutines.delay(1000)
                        timeLeft--
                    }
                    isRunning = false
                }
            }
        }) {
            Text("Start Pomodoro")
        }
    }
}
