package com.hellomotem.horserace.history.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hellomotem.horserace.R
import com.hellomotem.horserace.databinding.FragmentRaceHistoryScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RaceHistoryFragment: Fragment(R.layout.fragment_race_history_screen) {

    private var _binding: FragmentRaceHistoryScreenBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: RacesHistoryViewModel by viewModels()

    private val adapter = RacesHistoryAdapter { item ->
        viewModel.dispatchEvent(
            RacesHistoryViewModel.Event.DeleteHistoryItem(item)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceHistoryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect(::setupState)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.raceHistoryRecyclerView.adapter = null
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.raceHistoryRecyclerView.adapter = adapter

        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.race_history_item_divider)
        drawable?.let(divider::setDrawable)

        binding.raceHistoryRecyclerView.addItemDecoration(divider)
    }

    private fun setupState(state: RacesHistoryViewModel.State) {
        adapter.items = state.items

        binding.raceHistoryRecyclerView.isVisible = state.items.isNotEmpty()
        binding.emptyListPlaceholder.isVisible = state.items.isEmpty()
    }
}