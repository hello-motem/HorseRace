package com.hellomotem.horserace.race.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hellomotem.horserace.R
import com.hellomotem.horserace.databinding.FragmentRaceScreenBinding
import com.hellomotem.horserace.delegates.stringRes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RaceFragment: Fragment(R.layout.fragment_race_screen) {

    private val viewModel: RaceViewModel by viewModels()

    private var _binding: FragmentRaceScreenBinding? = null
    private val binding: FragmentRaceScreenBinding get() = requireNotNull(_binding)

    private val stopButtonText by stringRes(R.string.stop_button_text)
    private val startButtonText by stringRes(R.string.start_button_text)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startButton.setOnClickListener {
            viewModel.dispatchEvent(RaceViewModel.Event.OnStartClicked)
        }

        binding.restartButton.setOnClickListener {
            viewModel.dispatchEvent(RaceViewModel.Event.OnRestartClicked)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::setupState)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupState(state: RaceViewModel.State): Unit = with(binding) {
        timerTextview.text = state.time.formatted()
        startButton.text = when(state.raceState) {
            RaceViewModel.State.RaceState.INITIAL -> startButtonText
            RaceViewModel.State.RaceState.STARTED -> stopButtonText
            RaceViewModel.State.RaceState.STOPPED -> startButtonText
        }
        restartButton.isEnabled = state.time != RaceTimeUi.ZERO
        startButton.isEnabled = when(state.raceState) {
            RaceViewModel.State.RaceState.INITIAL -> true
            RaceViewModel.State.RaceState.STARTED -> true
            RaceViewModel.State.RaceState.STOPPED -> false
        }
    }
}