package com.plub.presentation.ui.home.bookmark

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSearchType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.state.PageState

data class BookmarkPageState(
    val bookmarkList:List<PlubCardVo> = emptyList(),
    val cardType:PlubCardType = PlubCardType.LIST,
    val isEmptyViewVisible:Boolean = true
): PageState