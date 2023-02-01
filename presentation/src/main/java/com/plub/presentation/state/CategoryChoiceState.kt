package com.plub.presentation.state

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState

data class CategoryChoiceState(
    val cardList: List<PlubCardVo> = emptyList(),
    val cardType: PlubCardType = PlubCardType.LIST,
    val isEmptyViewVisible: Boolean = false,
    val sortType: PlubSortType = PlubSortType.POPULAR
) : PageState