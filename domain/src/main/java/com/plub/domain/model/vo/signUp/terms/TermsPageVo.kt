package com.plub.domain.model.vo.signUp.terms

import com.plub.domain.model.vo.signUp.SignUpPageVo

data class TermsPageVo(
    val privacy: Boolean = false,
    val location: Boolean = false,
    val age: Boolean = false,
    val collect: Boolean = false,
    val marketing: Boolean = false,
) : SignUpPageVo