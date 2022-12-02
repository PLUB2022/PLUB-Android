package com.plub.presentation.ui.createGathering.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.presentation.ui.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNameFragment
import com.plub.presentation.ui.createGathering.selectPlubCategory.SelectPlubCategoryFragment
import com.plub.presentation.util.ViewPager.CREATE_GATHERING_PAGE_SIZE

class FragmentCreateGatheringPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = CREATE_GATHERING_PAGE_SIZE

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> SelectPlubCategoryFragment()
        1 -> CreateGatheringTitleAndNameFragment()
        else -> throw IllegalAccessException()
    }
}
