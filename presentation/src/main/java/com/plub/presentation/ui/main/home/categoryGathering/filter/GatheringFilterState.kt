package com.plub.presentation.ui.main.home.categoryGathering.filter

import android.os.Parcelable
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.presentation.ui.PageState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class GatheringFilterState(
    val gatheringDays: HashSet<DaysType> = hashSetOf(),
    val categoryName : String ="",
    val subHobbies : @RawValue List<SubHobbyVo> = emptyList(),
    val seekBarProgress: Int = 0,
    val seekBarPositionX: Float = 0.0f,
    val accountNum : Int = 0,
    val hobbiesSelectedVo: @RawValue SignUpHobbiesVo = SignUpHobbiesVo()
): PageState, Parcelable {
    @IgnoredOnParcel
    val isButtonEnable = hobbiesSelectedVo.hobbies.isNotEmpty() && gatheringDays.isNotEmpty()
    val peopleNumber = seekBarProgress + 4
}
