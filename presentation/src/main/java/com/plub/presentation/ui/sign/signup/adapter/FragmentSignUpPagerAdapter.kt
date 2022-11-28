package com.plub.presentation.ui.sign.signup.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.vo.signUp.SignUpListener
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.presentation.ui.sign.personalInfo.PersonalInfoFragment
import com.plub.presentation.ui.sign.signup.SignUpFragment
import com.plub.presentation.ui.sign.terms.TermsFragment

class FragmentSignUpPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle,
    private val delegate: SignUpFragment.Delegate,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val signUpListener: MutableMap<SignUpPageType, SignUpListener> = mutableMapOf()

    fun initPage(pageType: SignUpPageType, pageVo: SignUpPageVo?) {
        signUpListener[pageType]?.initPage(pageVo)
    }

    override fun getItemCount(): Int = SignUpPageType.values().size

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            SignUpPageType.TERMS.idx -> TermsFragment.newInstance(delegate)
            SignUpPageType.PERSONAL_INFO.idx -> PersonalInfoFragment.newInstance(delegate)
            SignUpPageType.PROFILE.idx -> TermsFragment.newInstance(delegate)
            SignUpPageType.MORE_INFO.idx -> TermsFragment.newInstance(delegate)
            SignUpPageType.HOBBY.idx -> TermsFragment.newInstance(delegate)
            else -> throw IllegalAccessException()
        }
        signUpListener[SignUpPageType.valueOf(position)] = fragment
        return fragment
    }
}
