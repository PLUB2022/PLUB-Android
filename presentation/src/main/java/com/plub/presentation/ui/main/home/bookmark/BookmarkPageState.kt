package com.plub.presentation.ui.main.home.bookmark

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class BookmarkPageState(
    val bookmarkList: StateFlow<List<PlubCardVo>>,
    val cardType: StateFlow<PlubCardType>,
    val isEmptyViewMode: StateFlow<Boolean>,
) : PageState