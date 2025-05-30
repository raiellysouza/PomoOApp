// app/src/main/java/com/example/pomoappl/viewmodel/TimerViewModel.kt
package com.example.pomoappl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    // Duração padrão do ciclo Pomodoro (em segundos)
    private val POMODORO_FOCUS_DURATION_SECONDS = 25 * 60 // 25 minutos
    private val POMODORO_SHORT_BREAK_DURATION_SECONDS = 5 * 60 // 5 minutos
    private val POMODORO_LONG_BREAK_DURATION_SECONDS = 15 * 60 // 15 minutos
    private val CYCLES_BEFORE_LONG_BREAK = 4 // 4 ciclos de foco antes da pausa longa

    // Estado atual do tempo restante (em segundos)
    private val _timeLeft = MutableStateFlow(POMODORO_FOCUS_DURATION_SECONDS)
    val timeLeft: StateFlow<Int> = _timeLeft.asStateFlow()

    // Estado de execução do cronômetro
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    // Tipo atual do ciclo (foco, pausa curta, pausa longa)
    private val _currentCycleType = MutableStateFlow(CycleType.FOCUS)
    val currentCycleType: StateFlow<CycleType> = _currentCycleType.asStateFlow()

    // Contador de ciclos de foco concluídos
    private val _focusCyclesCompleted = MutableStateFlow(0)
    val focusCyclesCompleted: StateFlow<Int> = _focusCyclesCompleted.asStateFlow()

    private var timerJob: Job? = null

    // Enum para os tipos de ciclo
    enum class CycleType {
        FOCUS, SHORT_BREAK, LONG_BREAK
    }

    /**
     * Inicia ou retoma o cronômetro Pomodoro.
     */
    fun startTimer() {
        if (!_isRunning.value) {
            _isRunning.value = true
            timerJob = viewModelScope.launch {
                while (_timeLeft.value > 0 && _isRunning.value) {
                    delay(1000) // Espera 1 segundo
                    _timeLeft.value--
                }
                if (_timeLeft.value == 0) {
                    // Ciclo terminou, avança para o próximo
                    moveToNextCycle()
                }
            }
        }
    }

    /**
     * Pausa o cronômetro.
     */
    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    /**
     * Reseta o cronômetro para o início do ciclo de foco.
     */
    fun resetTimer() {
        _isRunning.value = false
        timerJob?.cancel()
        _timeLeft.value = POMODORO_FOCUS_DURATION_SECONDS
        _currentCycleType.value = CycleType.FOCUS
        _focusCyclesCompleted.value = 0
    }

    /**
     * Avança para o próximo tipo de ciclo (foco, pausa curta, pausa longa).
     */
    private fun moveToNextCycle() {
        _isRunning.value = false // Pausa automaticamente ao mudar de ciclo
        when (_currentCycleType.value) {
            CycleType.FOCUS -> {
                _focusCyclesCompleted.value++
                if (_focusCyclesCompleted.value % CYCLES_BEFORE_LONG_BREAK == 0) {
                    _currentCycleType.value = CycleType.LONG_BREAK
                    _timeLeft.value = POMODORO_LONG_BREAK_DURATION_SECONDS
                } else {
                    _currentCycleType.value = CycleType.SHORT_BREAK
                    _timeLeft.value = POMODORO_SHORT_BREAK_DURATION_SECONDS
                }
            }
            CycleType.SHORT_BREAK -> {
                _currentCycleType.value = CycleType.FOCUS
                _timeLeft.value = POMODORO_FOCUS_DURATION_SECONDS
            }
            CycleType.LONG_BREAK -> {
                _currentCycleType.value = CycleType.FOCUS
                _timeLeft.value = POMODORO_FOCUS_DURATION_SECONDS
            }
        }
        // Opcional: iniciar o próximo ciclo automaticamente ou esperar o usuário
        // startTimer()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}