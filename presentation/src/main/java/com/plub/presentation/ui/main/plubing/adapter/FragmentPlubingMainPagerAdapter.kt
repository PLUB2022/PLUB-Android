package com.plub.presentation.ui.main.plubing.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.enums.SignUpPageType
import com.plub.presentation.ui.main.plubing.board.PlubingBoardFragment
import com.plub.presentation.ui.main.plubing.todo.PlubingTodoFragment
import com.plub.presentation.ui.sign.hobbies.HobbiesFragment
import com.plub.presentation.ui.sign.moreInfo.MoreInfoFragment
import com.plub.presentation.ui.sign.personalInfo.PersonalInfoFragment
import com.plub.presentation.ui.sign.profileCompose.ProfileComposeFragment
import com.plub.presentation.ui.sign.terms.TermsFragment

class FragmentPlubingMainPagerAdapter(
    fragment: Fragment,
    private val plubingId:Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = PlubingMainPageType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (PlubingMainPageType.valueOf(position)) {
            PlubingMainPageType.BOARD -> PlubingBoardFragment.newInstance(plubingId)
            PlubingMainPageType.TODO_LIST -> PlubingTodoFragment()
        }
    }
}
