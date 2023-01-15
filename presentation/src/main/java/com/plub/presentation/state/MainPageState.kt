package com.plub.presentation.state

import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringDataResponseVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo

data class MainPageState(
    val categoryVo :CategoryListResponseVo = CategoryListResponseVo(CategoryListDataResponseVo(
        emptyList()
    )),
    val recommendationGatheringVo : RecommendationGatheringResponseVo = RecommendationGatheringResponseVo(RecommendationGatheringDataResponseVo
        (
        emptyList(),
        false,
        false,
        false,
        0,
        0,
        0,
        0,
        0
                ))
):PageState
