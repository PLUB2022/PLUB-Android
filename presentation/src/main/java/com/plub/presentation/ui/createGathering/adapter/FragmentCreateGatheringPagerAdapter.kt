package com.plub.presentation.ui.createGathering.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.presentation.ui.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNameFragment
import com.plub.presentation.ui.createGathering.goalAndIntroduceAndPicture.CreateGatheringGoalAndIntroduceAndPictureFragment
import com.plub.presentation.ui.createGathering.selectPlubCategory.SelectPlubCategoryFragment

class FragmentCreateGatheringPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = CreateGatheringPageType.values().size

    override fun createFragment(position: Int): Fragment = when(position) {
        CreateGatheringPageType.SELECT_PLUB_CATEGORY.idx -> SelectPlubCategoryFragment()
        CreateGatheringPageType.GATHERING_TITLE_AND_NAME.idx -> CreateGatheringTitleAndNameFragment()
        CreateGatheringPageType.GOAL_INTRODUCE_PICTURE.idx -> CreateGatheringGoalAndIntroduceAndPictureFragment()
        else -> throw IllegalAccessException()
    }
}
