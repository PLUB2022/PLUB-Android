package com.plub.presentation.ui.main.gathering.my

import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.domain.model.vo.myGathering.MyGatheringsResponseVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class MyGatheringPageState(
    val myGatherings: StateFlow<List<MyGatheringResponseVo>>,
    val myHostings: StateFlow<List<MyGatheringResponseVo>>
) : PageState