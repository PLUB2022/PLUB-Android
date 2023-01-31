package com.plub.presentation.ui.main.home.bookmark

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState

data class BookmarkPageState(
    val bookmarkList:List<PlubCardVo> = emptyList(),
    val cardType:PlubCardType = PlubCardType.LIST,
    val isEmptyViewVisible:Boolean = true
): PageState