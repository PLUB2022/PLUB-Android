package com.plub.presentation.ui.onboarding

import com.plub.domain.model.vo.onboarding.OnboardingItemVo
import com.plub.presentation.ui.PageState

data class OnboardingPageState(
    val onboardingDataList:List<OnboardingItemVo> = emptyList(),
    val currentPage:Int = 0
): PageState