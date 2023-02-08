package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.PlubHomeRecommendViewType
import com.plub.domain.model.vo.plub.PlubCardVo

data class RecommendationGatheringResponseVo(
    val viewType : PlubHomeRecommendViewType = PlubHomeRecommendViewType.RECOMMEND_VIEW,
    val content: List<PlubCardVo> = emptyList()
): DomainModel