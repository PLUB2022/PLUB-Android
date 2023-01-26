package com.plub.presentation.state

import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

data class MainPageState(
    val categoryVo :CategoryListResponseVo = CategoryListResponseVo(CategoryListDataResponseVo(
        emptyList()
    ))
):PageState