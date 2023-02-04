package com.plub.presentation.ui.main.home.search

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState

data class SearchingPageState(
    val isRecentSearchVisibility:Boolean = true,
    val recentSearchList:List<RecentSearchVo> = emptyList(),
    val isRecentSearchEmptyViewVisibility:Boolean = true,
    val searchList: List<PlubCardVo> = emptyList(),
    val isSearchEmptyViewVisibility: Boolean = true,
    val isSearchTextEmpty: Boolean = true,
    val cardType: PlubCardType = PlubCardType.LIST,
    val sortType: PlubSortType = PlubSortType.POPULAR,
    var searchText:String = ""
): PageState