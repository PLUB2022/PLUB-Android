package com.plub.domain.model.state

import com.plub.domain.model.vo.onboarding.OnboardingItemVo

data class OnboardingPageState(
    val onboardingDataList:List<OnboardingItemVo> = emptyList(),
    val currentPage:Int = 0
): PageState