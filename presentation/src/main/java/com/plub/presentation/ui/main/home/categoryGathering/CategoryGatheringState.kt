package com.plub.presentation.ui.main.home.categoryGathering

import com.plub.domain.model.enums.PlubCardType
import com.plub.domain.model.enums.PlubSortType
import com.plub.domain.model.vo.plub.PlubCardVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class CategoryGatheringState(
    val categoryName : StateFlow<String>,
    val cardList: StateFlow<List<PlubCardVo>>,
    val cardType: StateFlow<PlubCardType>,
    val isEmptyViewVisible: StateFlow<Boolean>,
    val sortType: StateFlow<PlubSortType>,
    val sortTypeName : StateFlow<String>
) : PageState