package com.plub.presentation.ui.sign.signup.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.SignUpPageType
import com.plub.presentation.ui.sign.personalInfo.PersonalInfoFragment
import com.plub.presentation.ui.sign.signup.SignUpFragment
import com.plub.presentation.ui.sign.terms.TermsFragment

class FragmentSignUpPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 10

//    override fun getItemCount(): Int = SignUpPageType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SignUpPageType.TERMS.idx -> TermsFragment()
            SignUpPageType.PERSONAL_INFO.idx -> PersonalInfoFragment()
            SignUpPageType.PROFILE.idx -> TermsFragment()
            SignUpPageType.MORE_INFO.idx -> TermsFragment()
            SignUpPageType.HOBBY.idx -> TermsFragment()
            else -> TermsFragment()
//            else -> throw IllegalAccessException()
        }
    }
}
