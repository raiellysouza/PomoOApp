package com.example.pomoappl.ui.compose.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val POMODORO_FOCUS_DURATION_SECONDS = 25 * 60
    private val POMODORO_SHORT_BREAK_DURATION_SECONDS = 5 * 60
    private val POMODORO_LONG_BREAK_DURATION_SECONDS = 15 * 60
    private val CYCLES_BEFORE_LONG_BREAK = 4

    private val _timeLeft = MutableStateFlow(POMODORO_FOCUS_DURATION_SECONDS)
    val timeLeft: StateFlow<Int> = _timeLeft.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _currentCycleType = MutableStateFlow(CycleType.FOCUS)
    val currentCycleType: StateFlow<CycleType> = _currentCycleType.asStateFlow()

    private val _focusCyclesCompleted = MutableStateFlow(0)
    val focusCyclesCompleted: StateFlow<Int> = _focusCyclesCompleted.asStateFlow()

    private var timerJob: Job? = null

    enum class CycleType {
        FOCUS, SHORT_BREAK, LONG_BREAK
    }

    fun startTimer() {
        if (!_isRunning.value) {
            _isRunning.value = true
            timerJob = viewModelScope.launch {
                while (_timeLeft.value > 0 && _isRunning.value) {
                    delay(1000)
                    _timeLeft.value--
                }
                if (_timeLeft.value == 0) {
              
                    moveToNextCycle()
                }
            }
        }
    }

    fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        _isRunning.value = false
        timerJob?.cancel()
        _timeLeft.value = POMODORO_FOCUS_DURATION_SECONDS
        _currentCycleType.value = CycleType.FOCUS
        _focusCyclesCompleted.value = 0
    }

    private fun moveToNextCycle() {
        _isRunning.value = false
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
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    private fun NavGraph() {
        TODO("Not yet implemented")
    }
}

