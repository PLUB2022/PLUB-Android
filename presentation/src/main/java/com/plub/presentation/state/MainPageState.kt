package com.plub.presentation.state

import com.plub.domain.model.enums.MainPageCategoryPlubType
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo
import com.plub.domain.model.vo.plub.PlubCardListVo

data class MainPageState(
    val categoryVo :CategoryListDataResponseVo= CategoryListDataResponseVo(emptyList()),
    val plubCardList : PlubCardListVo = PlubCardListVo(),
    val categoryOrPlub : MainPageCategoryPlubType = MainPageCategoryPlubType.CATEGORY
):PageState