package com.plub.presentation.ui.main.home.searchResult

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState

data class SearchResultPageState(
    val searchList: List<PlubCardVo> = emptyList(),
    val cardType: PlubCardType = PlubCardType.LIST,
    val isEmptyViewVisible: Boolean = true,
    val sortType: PlubSortType = PlubSortType.POPULAR
) : PageState