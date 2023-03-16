package com.plub.presentation.ui.main.home.search

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class SearchingPageState(
    val isRecentSearchMode: StateFlow<Boolean>,
    val recentSearchList: StateFlow<List<RecentSearchVo>>,
    val searchList: StateFlow<List<PlubCardVo>>,
    val cardType: StateFlow<PlubCardType>,
    val sortType: StateFlow<PlubSortType>,
    val isSearchedTextEmpty: StateFlow<Boolean>,
    val searchText: MutableStateFlow<String>,
) : PageState