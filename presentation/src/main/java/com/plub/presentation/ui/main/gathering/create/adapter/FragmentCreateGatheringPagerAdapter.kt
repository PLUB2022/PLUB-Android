package com.plub.presentation.ui.main.gathering.create.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationFragment
import com.plub.presentation.ui.main.gathering.create.finish.CreateGatheringFinishFragment
import com.plub.presentation.ui.main.gathering.create.gatheringTitleAndName.CreateGatheringTitleAndNameFragment
import com.plub.presentation.ui.main.gathering.create.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndImageFragment
import com.plub.presentation.ui.main.gathering.create.peopleNumber.CreateGatheringPeopleNumberFragment
import com.plub.presentation.ui.main.gathering.create.preview.CreateGatheringPreviewFragment
import com.plub.presentation.ui.main.gathering.create.question.CreateGatheringQuestionFragment
import com.plub.presentation.ui.main.gathering.create.selectPlubCategory.CreateGatheringSelectPlubCategoryFragment

class FragmentCreateGatheringPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = CreateGatheringPageType.values().size

    override fun createFragment(position: Int): Fragment = when(position) {
        CreateGatheringPageType.SELECT_PLUB_CATEGORY.idx -> CreateGatheringPeopleNumberFragment() //CreateGatheringSelectPlubCategoryFragment()
        CreateGatheringPageType.GATHERING_TITLE_AND_NAME.idx -> CreateGatheringTitleAndNameFragment()
        CreateGatheringPageType.GOAL_INTRODUCE_PICTURE.idx -> CreateGatheringGoalAndIntroduceAndImageFragment()
        CreateGatheringPageType.DAY_ON_OFF_LOCATION.idx -> CreateGatheringDayAndTimeAndOnOfflineAndLocationFragment()
        CreateGatheringPageType.PEOPLE_NUMBER.idx -> CreateGatheringPeopleNumberFragment()
        CreateGatheringPageType.QUESTION.idx -> CreateGatheringQuestionFragment()
        CreateGatheringPageType.PREVIEW.idx -> CreateGatheringPreviewFragment()
        CreateGatheringPageType.FINISH.idx -> CreateGatheringFinishFragment()
        else -> throw IllegalAccessException()
    }
}
