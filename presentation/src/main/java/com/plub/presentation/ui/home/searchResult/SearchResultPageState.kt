package com.plub.presentation.ui.home.searchResult

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.state.PageState

data class SearchResultPageState(
    val searchList: List<PlubCardVo> = emptyList(),
    val cardType: PlubCardType = PlubCardType.LIST,
    val isEmptyViewVisible: Boolean = true,
    val sortType: PlubSortType = PlubSortType.POPULAR
) : PageState