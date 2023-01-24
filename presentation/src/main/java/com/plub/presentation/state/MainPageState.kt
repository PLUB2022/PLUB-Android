package com.plub.presentation.state

import com.plub.domain.model.vo.home.CategoryListResponseVo
import com.plub.domain.model.vo.home.categorylistresponsevo.CategoryListDataResponseVo

data class MainPageState(
    val categoryVo :CategoryListResponseVo = CategoryListResponseVo(CategoryListDataResponseVo(
        emptyList()
    ))
):PageState
