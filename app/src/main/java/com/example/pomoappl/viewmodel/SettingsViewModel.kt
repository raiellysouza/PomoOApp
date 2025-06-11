package com.example.pomoappl.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel : ViewModel() {

    //Modo escuro
    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> = _isDarkModeEnabled.asStateFlow()

    private val _areNotificationsEnabled = MutableStateFlow(true)
    val areNotificationsEnabled: StateFlow<Boolean> = _areNotificationsEnabled.asStateFlow()

    fun toggleDarkMode() {
        _isDarkModeEnabled.value = !_isDarkModeEnabled.value
    }

    fun toggleNotifications() {
        _areNotificationsEnabled.value = !_areNotificationsEnabled.value
    }
}