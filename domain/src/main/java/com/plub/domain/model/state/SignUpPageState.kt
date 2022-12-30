package com.plub.domain.model.state

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo

data class SignUpPageState(
    val currentPage:Int = 0,
    val termsPageVo: TermsPageVo = TermsPageVo(),
    val personalInfoVo: PersonalInfoVo = PersonalInfoVo(),
    val profileComposeVo: ProfileComposeVo = ProfileComposeVo(),
): PageState