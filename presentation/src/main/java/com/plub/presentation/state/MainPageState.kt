package com.plub.presentation.state

import com.plub.domain.model.enums.MainPageCategoryPlubType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo
import com.plub.presentation.ui.PageState

data class MainPageState(
    val hasInterest : Boolean = false,
    val categoryVo :CategoryListDataResponseVo= CategoryListDataResponseVo(emptyList()),
    val plubCardList : List<RecommendationGatheringResponseVo> = emptyList(),
    val categoryOrPlub : MainPageCategoryPlubType = MainPageCategoryPlubType.CATEGORY
): PageState