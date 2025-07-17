package com.hellomotem.horserace.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hellomotem.horserace.race.presentation.RaceFragment

class ViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = FRAGMENTS_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> RaceFragment()
            1 -> RaceFragment()
            else -> error("Unknown position $position")
        }
    }
}

private const val FRAGMENTS_COUNT = 2