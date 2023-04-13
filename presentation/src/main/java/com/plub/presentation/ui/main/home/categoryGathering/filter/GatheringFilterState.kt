package com.plub.presentation.ui.main.home.categoryGathering.filter

import android.os.Parcelable
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.presentation.ui.PageState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GatheringFilterState(
    val gatheringDays: HashSet<DaysType> = hashSetOf(),
    val categoryName : String ="",
    val subHobbies : List<SubHobbyVo> = emptyList(),
    val seekBarProgress: Int = 0,
    val seekBarPositionX: Float = 0.0f,
    val accountNum : Int = 0,
    val isButtonEnable : Boolean = false,
    val selectedHobbies:List<SelectedHobbyVo> = emptyList()
): PageState, Parcelable {
    @IgnoredOnParcel
    val peopleNumber = seekBarProgress + 4
}
