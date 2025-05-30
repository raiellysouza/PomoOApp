// app/src/main/java/com.example.pomoappl/viewmodel/SettingsViewModel.kt
package com.example.pomoappl.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    // Estado para o modo escuro
    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> = _isDarkModeEnabled.asStateFlow()

    // Estado para notificações
    private val _areNotificationsEnabled = MutableStateFlow(true)
    val areNotificationsEnabled: StateFlow<Boolean> = _areNotificationsEnabled.asStateFlow()

    /**
     * Alterna o estado do modo escuro.
     */
    fun toggleDarkMode() {
        _isDarkModeEnabled.value = !_isDarkModeEnabled.value
    }

    /**
     * Alterna o estado das notificações.
     */
    fun toggleNotifications() {
        _areNotificationsEnabled.value = !_areNotificationsEnabled.value
    }

    // Você pode adicionar mais estados e lógicas para outras configurações aqui
}