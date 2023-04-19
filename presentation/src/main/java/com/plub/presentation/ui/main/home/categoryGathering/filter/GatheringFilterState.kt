package com.plub.presentation.ui.main.home.categoryGathering.filter

import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class GatheringFilterState(
    val gatheringDays: StateFlow<HashSet<DaysType>>,
    val categoryName : StateFlow<String>,
    val subHobbies : StateFlow<List<SubHobbyVo>>,
    val seekBarProgress: StateFlow<Int>,
    val seekBarPositionX: StateFlow<Float>,
    val accountNum : StateFlow<Int>,
    val isButtonEnable : StateFlow<Boolean>,
    val selectedHobbies: StateFlow<List<SelectedHobbyVo>>
): PageState {
    val peopleNumber = seekBarProgress.value + 4
}
