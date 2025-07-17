package com.hellomotem.horserace.history.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hellomotem.horserace.R
import com.hellomotem.horserace.databinding.RaceHistoryListItemBinding

class RacesHistoryAdapter(
    private val onItemDeleted: (RaceHistoryItemUi) -> Unit
): ListAdapter<RaceHistoryItemUi, RacesHistoryAdapter.ViewHolder>(DiffUtilCallback()) {

    var items: List<RaceHistoryItemUi>
        get() = currentList
        set(value) = submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = RaceHistoryListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding, onItemDeleted)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = getItem(position)
        .let(holder::bind)

    class ViewHolder(
        private val binding: RaceHistoryListItemBinding,
        private val onItemDeleted: (RaceHistoryItemUi) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RaceHistoryItemUi) {
            binding.raceHistoryDate.text = item.dateOfRace
            binding.raceHistoryTime.text = item.raceTime
            binding.deleteRaceButton.setOnClickListener { onItemDeleted(item) }
        }
    }

    private class DiffUtilCallback: DiffUtil.ItemCallback<RaceHistoryItemUi>() {
        override fun areItemsTheSame(
            oldItem: RaceHistoryItemUi,
            newItem: RaceHistoryItemUi
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RaceHistoryItemUi,
            newItem: RaceHistoryItemUi
        ): Boolean = oldItem == newItem

    }
}

