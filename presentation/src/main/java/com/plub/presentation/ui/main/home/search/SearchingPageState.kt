package com.plub.presentation.ui.main.home.search

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState

data class SearchingPageState(
    val isRecentSearchVisibility: Boolean = true,
    val recentSearchList: List<RecentSearchVo> = emptyList(),
    val searchList: List<PlubCardVo> = emptyList(),
    val cardType: PlubCardType = PlubCardType.LIST,
    val sortType: PlubSortType = PlubSortType.POPULAR,
    val searchTextIsEmpty: Boolean = true,
    var searchText: String = ""
) : PageState