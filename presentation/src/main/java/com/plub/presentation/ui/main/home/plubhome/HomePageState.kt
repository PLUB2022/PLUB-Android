package com.plub.presentation.ui.main.home.plubhome

import com.plub.domain.model.enums.MainPageCategoryPlubType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.ui.PageState

data class HomePageState(
    val hasInterest : Boolean = false,
    val categoryVo :CategoryListDataResponseVo= CategoryListDataResponseVo(emptyList()),
    val plubCardList : List<RecommendationGatheringResponseVo> = emptyList(),
    val categoryOrPlub : MainPageCategoryPlubType = MainPageCategoryPlubType.CATEGORY
): PageState