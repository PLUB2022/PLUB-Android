package com.plub.domain.model.vo.signUp.profile

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.SocialLoginType
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.model.vo.signUp.moreInfo.MoreInfoVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.domain.model.vo.signUp.terms.TermsPageVo
import kotlinx.coroutines.CoroutineScope

data class NicknameCheckRequestVo(
    val nickname:String,
    val scope: CoroutineScope
): DomainModel()