package com.plub.presentation.ui.sign.signup.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.SignUpPageType
import com.plub.presentation.ui.sign.hobbies.HobbiesFragment
import com.plub.presentation.ui.sign.signup.authentication.GetPhoneFragment
import com.plub.presentation.ui.sign.signup.personalInfo.PersonalInfoFragment
import com.plub.presentation.ui.sign.signup.profileCompose.ProfileComposeFragment
import com.plub.presentation.ui.sign.signup.terms.TermsFragment

class FragmentSignUpPagerAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = SignUpPageType.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SignUpPageType.TERMS.idx -> TermsFragment()
            SignUpPageType.AUTHENTICATION.idx -> GetPhoneFragment()
            SignUpPageType.PERSONAL_INFO.idx -> PersonalInfoFragment()
            SignUpPageType.PROFILE.idx -> ProfileComposeFragment()
            SignUpPageType.HOBBY.idx -> HobbiesFragment()
            else -> throw IllegalAccessException()
        }
    }
}
