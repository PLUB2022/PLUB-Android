package com.plub.presentation.ui.sign.signup.terms

import com.plub.domain.model.enums.TermsType
import com.plub.domain.model.vo.signUp.terms.TermsAgreementItemVo
import com.plub.presentation.ui.PageState

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