package com.plub.domain.model.vo.signUp.terms

import com.plub.domain.model.enums.TermsType

data class TermsAgreementItemVo(
    val termsType: TermsType,
    val title: String = "",
    val policy: String = "",
    val isExpanded: Boolean = false,
    var isChecked: Boolean = false
)