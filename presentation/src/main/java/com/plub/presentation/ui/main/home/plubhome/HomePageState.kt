package com.plub.presentation.ui.main.home.plubhome

import com.plub.domain.model.vo.home.HomePlubListVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class HomePageState(
    val homePlubList : StateFlow<List<HomePlubListVo>>
): PageState