package com.plub.presentation.ui.createGathering.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.presentation.ui.createGathering.selectPlubCategory.SelectPlubCategoryFragment

class FragmentCreateGatheringPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    companion object {
        private const val CREATE_GATHERING_SIZE = 1
    }

    override fun getItemCount(): Int = CREATE_GATHERING_SIZE

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> SelectPlubCategoryFragment()
        else -> throw IllegalAccessException()
    }
}
