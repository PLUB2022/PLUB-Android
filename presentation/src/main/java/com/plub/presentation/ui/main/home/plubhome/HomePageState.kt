package com.plub.presentation.ui.main.home.plubhome

import com.plub.domain.model.enums.HomeCategoryPlubType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.presentation.ui.PageState

data class HomePageState(
    val hasInterest : Boolean = false,
    val categories : List<CategoryListDataResponseVo> = emptyList(),
    val plubCardList : List<RecommendationGatheringResponseVo> = emptyList(),
    val categoryOrPlub : HomeCategoryPlubType = HomeCategoryPlubType.CATEGORY,
    val isLoading : Boolean = true
): PageState