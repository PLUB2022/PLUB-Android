package com.plub.domain.model.vo.home.categoriesGatheringVo

import com.plub.domain.model.DomainModel

data class CategoriesGatheringRequestVo(
    val paramsVo: CategoriesGatheringParamsVo,
    val bodyRequestVo: CategoriesGatheringBodyRequestVo
) : DomainModel