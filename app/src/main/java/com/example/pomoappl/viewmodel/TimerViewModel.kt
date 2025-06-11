import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val _timeLeft = MutableStateFlow(25 * 60)
    val timeLeft: StateFlow<Int> = _timeLeft

    private var timerJob: Job? = null

    fun startTimer() {
        if (timerJob?.isActive == true) return

        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value--
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun resetTimer(initialTime: Int = 25 * 60) {
        timerJob?.cancel()
        _timeLeft.value = initialTime
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}