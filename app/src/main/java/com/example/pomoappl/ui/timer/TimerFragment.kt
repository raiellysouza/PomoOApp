package com.example.pomoappl.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pomoappl.databinding.FragmentTimerBinding
import com.example.pomoappl.viewmodel.TimerViewModel

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            viewModel.startTimer(25 * 60 * 1000L)
        }

        viewModel.timeLeft.observe(viewLifecycleOwner) {
            binding.timerText.text = "$it s"
        }

        viewModel.isRunning.observe(viewLifecycleOwner) {
            binding.statusText.text = if (it) "Running..." else "Stopped"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
