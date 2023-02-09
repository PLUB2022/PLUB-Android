package com.plub.domain.model.vo.home

import com.plub.domain.model.enums.HomeViewType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoriesDataResponseVo
import com.plub.domain.model.vo.plub.PlubCardVo

data class HomePlubListVo(
    val viewType: HomeViewType = HomeViewType.TITLE_VIEW,
    val categoryList : List<CategoriesDataResponseVo> = emptyList(),
    val recommendGathering : PlubCardVo = PlubCardVo()
)
