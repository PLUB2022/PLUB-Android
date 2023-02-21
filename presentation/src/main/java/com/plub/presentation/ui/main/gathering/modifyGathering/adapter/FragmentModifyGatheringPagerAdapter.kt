package com.plub.presentation.ui.main.gathering.modifyGathering.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.ModifyGatheringPageType
import com.plub.presentation.ui.main.gathering.modifyGathering.ModifyGatheringPageState
import com.plub.presentation.ui.main.gathering.modifyGathering.recruit.ModifyRecruitFragment

class FragmentModifyGatheringPagerAdapter(fragment: Fragment, private val pageState: ModifyGatheringPageState): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = ModifyGatheringPageType.values().size

    override fun createFragment(position: Int): Fragment = when(position) {
        ModifyGatheringPageType.RECRUIT.idx -> ModifyRecruitFragment.newInstance(initPageState = pageState.modifyRecruitPageState)
        ModifyGatheringPageType.INFO.idx -> ModifyRecruitFragment.newInstance(initPageState = pageState.modifyRecruitPageState)
        ModifyGatheringPageType.GUEST_QUESTION.idx -> ModifyRecruitFragment.newInstance(initPageState = pageState.modifyRecruitPageState)
        else -> throw IllegalAccessException()
    }
}
