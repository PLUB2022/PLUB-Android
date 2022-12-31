package com.plub.presentation.state

import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.signUp.terms.TermsAgreementItemVo

data class TermsPageState(
    val isNextButtonEnable:Boolean = false,
    val mapVo:Map<TermsType, TermsAgreementItemVo> = hashMapOf(
        TermsType.ALL to TermsAgreementItemVo(TermsType.ALL),
        TermsType.PRIVACY to TermsAgreementItemVo(TermsType.PRIVACY),
        TermsType.LOCATION to TermsAgreementItemVo(TermsType.LOCATION),
        TermsType.AGE to TermsAgreementItemVo(TermsType.AGE),
        TermsType.COLLECT to TermsAgreementItemVo(TermsType.COLLECT),
        TermsType.MARKETING to TermsAgreementItemVo(TermsType.MARKETING),
    ),
): PageState