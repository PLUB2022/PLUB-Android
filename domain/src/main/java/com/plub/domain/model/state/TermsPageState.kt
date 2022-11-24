package com.plub.domain.model.state

import com.plub.domain.UiState
import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.login.SampleLogin
import com.plub.domain.model.vo.login.SocialLoginResponseVo
import com.plub.domain.model.vo.terms.TermsAgreementItemVo

data class TermsPageState(
    val isNextButtonEnable:Boolean = false,
    val mapVo:Map<TermsType,TermsAgreementItemVo> = hashMapOf(
        TermsType.ALL to TermsAgreementItemVo(TermsType.ALL),
        TermsType.PRIVACY to TermsAgreementItemVo(TermsType.PRIVACY),
        TermsType.LOCATION to TermsAgreementItemVo(TermsType.LOCATION),
        TermsType.AGE to TermsAgreementItemVo(TermsType.AGE),
        TermsType.COLLECT to TermsAgreementItemVo(TermsType.COLLECT),
        TermsType.MARKETING to TermsAgreementItemVo(TermsType.MARKETING),
    ),
): PageState