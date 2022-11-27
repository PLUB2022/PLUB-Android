package com.plub.presentation.ui.sign.signup.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.SignUpPageType
import com.plub.presentation.ui.sign.personalInfo.PersonalInfoFragment
import com.plub.presentation.ui.sign.signup.SignUpFragment
import com.plub.presentation.ui.sign.terms.TermsFragment

class FragmentSignUpPagerAdapter(
    fragment: Fragment, private val delegate: SignUpFragment.Delegate
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = SignUpPageType.values().size

    override fun createFragment(position: Int): Fragment = when (position) {
        SignUpPageType.TERMS.idx -> TermsFragment.newInstance(delegate)
        SignUpPageType.PERSONAL_INFO.idx -> PersonalInfoFragment.newInstance(delegate)
        SignUpPageType.PROFILE.idx -> TermsFragment.newInstance(delegate)
        SignUpPageType.MORE_INFO.idx -> TermsFragment.newInstance(delegate)
        SignUpPageType.HOBBY.idx -> TermsFragment.newInstance(delegate)
        else -> throw IllegalAccessException()
    }
}
