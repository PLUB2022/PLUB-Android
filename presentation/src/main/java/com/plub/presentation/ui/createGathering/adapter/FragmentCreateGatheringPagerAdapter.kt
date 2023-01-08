package com.plub.presentation.ui.createGathering.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndOnOfflineAndLocationFragment
import com.plub.presentation.ui.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNameFragment
import com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndImageFragment
import com.plub.presentation.ui.createGathering.peopleNumber.CreateGatheringPeopleNumberFragment
import com.plub.presentation.ui.createGathering.question.CreateGatheringQuestionFragment
import com.plub.presentation.ui.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryFragment

class FragmentCreateGatheringPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = CreateGatheringPageType.values().size

    override fun createFragment(position: Int): Fragment = when(position) {
        CreateGatheringPageType.SELECT_PLUB_CATEGORY.idx -> CreateGatheringQuestionFragment() //CreateGatheringSelectPlubCategoryFragment()
        CreateGatheringPageType.GATHERING_TITLE_AND_NAME.idx -> CreateGatheringTitleAndNameFragment()
        CreateGatheringPageType.GOAL_INTRODUCE_PICTURE.idx -> CreateGatheringGoalAndIntroduceAndImageFragment()
        CreateGatheringPageType.DAY_ON_OFF_LOCATION.idx -> CreateGatheringDayAndOnOfflineAndLocationFragment()
        CreateGatheringPageType.PEOPLE_NUMBER.idx -> CreateGatheringPeopleNumberFragment()
        CreateGatheringPageType.QUESTION.idx -> CreateGatheringQuestionFragment()
        else -> throw IllegalAccessException()
    }
}
