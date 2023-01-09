package com.plub.domain.model.vo.signUp

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.SocialLoginType
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo

data class SignUpRequestVo(
    val signToken:String,
    val profileUrl:String,
    val termsPageVo: TermsPageVo,
    val personalInfoVo: PersonalInfoVo,
    val profileComposeVo: ProfileComposeVo,
    val moreInfoVo: MoreInfoVo,
    val hobbyInfoVo: SignUpHobbiesVo
): DomainModel()