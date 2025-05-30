// app/src/main/java/com/example/pomoappl/viewmodel/FavoritesViewModel.kt
package com.example.pomoappl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomoappl.data.model.PomodoroConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    // Lista de configurações de Pomodoro favoritas
    private val _favoriteConfigs = MutableStateFlow<MutableList<PomodoroConfig>>(mutableListOf(
        PomodoroConfig(1, "Padrão 25/5", 25, 5, 15, 4),
        PomodoroConfig(2, "Foco Intenso 45/10", 45, 10, 20, 3)
    ))
    val favoriteConfigs: StateFlow<List<PomodoroConfig>> = _favoriteConfigs.asStateFlow()

    /**
     * Adiciona uma configuração de Pomodoro aos favoritos.
     */
    fun addFavoriteConfig(config: PomodoroConfig) {
        viewModelScope.launch {
            _favoriteConfigs.update { currentList ->
                if (!currentList.contains(config)) {
                    currentList.apply { add(config) }
                } else {
                    currentList
                }
            }
        }
    }

    /**
     * Remove uma configuração de Pomodoro dos favoritos.
     */
    fun removeFavoriteConfig(config: PomodoroConfig) {
        viewModelScope.launch {
            _favoriteConfigs.update { currentList ->
                currentList.apply { remove(config) }
            }
        }
    }

    // Você pode adicionar lógica para carregar/salvar favoritos em armazenamento persistente aqui
}