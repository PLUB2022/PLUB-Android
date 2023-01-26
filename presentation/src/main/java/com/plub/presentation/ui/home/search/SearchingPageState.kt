package com.plub.presentation.ui.home.search

import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.presentation.state.PageState

data class SearchingPageState(
    val recentSearchList:List<RecentSearchVo> = emptyList(),
    val isEmptyViewVisible:Boolean = true,
): PageState