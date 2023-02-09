package com.plub.presentation.ui.main.home.categoryChoice

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState

data class CategoryGatheringState(
    val cardList: List<PlubCardVo> = emptyList(),
    val cardType: PlubCardType = PlubCardType.LIST,
    val isEmptyViewVisible: Boolean = false,
    val sortType: PlubSortType = PlubSortType.POPULAR,
    val isLoading : Boolean = true
) : PageState