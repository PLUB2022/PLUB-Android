package com.plub.domain.model.vo.home.recommendationgatheringvo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.MainHasDataType
import com.plub.domain.model.vo.plub.PlubCardVo

data class RecommendationGatheringResponseVo(
    val viewType : MainHasDataType = MainHasDataType.DATA,
    val content: List<PlubCardVo> = emptyList()
): DomainModel