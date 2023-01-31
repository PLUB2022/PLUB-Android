package com.plub.presentation.ui.sign.signup

import com.plub.domain.model.vo.signUp.SignUpRequestVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import com.plub.presentation.ui.PageState

data class SignUpPageState(
    val currentPage: Int = 0,
    val termsPageVo: TermsPageVo = TermsPageVo(),
    val personalInfoVo: PersonalInfoVo = PersonalInfoVo(),
    val profileComposeVo: ProfileComposeVo = ProfileComposeVo(),
    val moreInfoVo: MoreInfoVo = MoreInfoVo(),
    val hobbyInfoVo: SignUpHobbiesVo = SignUpHobbiesVo()
) : PageState {
    fun getSignUpRequestVo(signToken:String, profileUrl:String):SignUpRequestVo {
        return SignUpRequestVo(
            signToken,
            profileUrl,
            termsPageVo,
            personalInfoVo,
            profileComposeVo,
            moreInfoVo,
            hobbyInfoVo
        )
    }
}