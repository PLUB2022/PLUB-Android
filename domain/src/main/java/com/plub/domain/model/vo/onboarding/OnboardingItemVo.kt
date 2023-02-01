package com.plub.domain.model.vo.onboarding

import com.plub.domain.model.DomainModel

data class OnboardingItemVo(
    val lottieRes: Int,
    val title: String,
    val content: String
): DomainModel