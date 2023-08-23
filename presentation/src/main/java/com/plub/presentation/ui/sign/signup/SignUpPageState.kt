package com.plub.presentation.ui.sign.signup

import com.plub.domain.model.vo.signUp.SignUpRequestVo
import com.plub.domain.model.vo.signUp.authentication.PhoneNumberVo
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
    val hobbyInfoVo: SignUpHobbiesVo = SignUpHobbiesVo(),
    val phoneVo : PhoneNumberVo = PhoneNumberVo()
) : PageState {
    fun getSignUpRequestVo(signToken:String, fcmToken: String, profileUrl:String):SignUpRequestVo {
        return SignUpRequestVo(
            signToken,
            fcmToken,
            profileUrl,
            termsPageVo,
            personalInfoVo,
            profileComposeVo,
            hobbyInfoVo,
            phoneVo
        )
    }
}