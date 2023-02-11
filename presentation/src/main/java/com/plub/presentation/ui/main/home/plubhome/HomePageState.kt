package com.plub.presentation.ui.main.home.plubhome

import com.plub.domain.model.vo.home.HomePlubListVo
import com.plub.presentation.ui.PageState

data class HomePageState(
    val homePlubList : List<HomePlubListVo> = emptyList(),
    val isVisible : Boolean = false,
    val isLoading : Boolean = false
): PageState