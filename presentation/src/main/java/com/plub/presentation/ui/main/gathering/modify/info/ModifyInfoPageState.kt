package com.plub.presentation.ui.main.gathering.modify.info

import android.os.Parcelable
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.ui.PageState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class ModifyInfoPageState(
    val plubbingId: Int = -1,
    val gatheringDays: HashSet<DaysType> = hashSetOf(),
    val gatheringOnOffline: String = "",
    val address : String = "",
    val roadAdress : String = "",
    val placeName : String = "",
    val gatheringHour: Int = 19,
    val gatheringMin: Int = 0,
    val gatheringFormattedTime: String = "",
    val seekBarProgress: Int = 0,
    val seekBarPositionX: Float = 0.0f
) : PageState, Parcelable {
    @IgnoredOnParcel
    val peopleNumber = seekBarProgress + 4

    @IgnoredOnParcel
    val isNextButtonEnabled = gatheringDays.isNotEmpty() && gatheringFormattedTime.isNotEmpty() &&
            if(gatheringOnOffline == OnOfflineType.OFF.value)
                placeName.isNotBlank()
            else gatheringOnOffline == OnOfflineType.ON.value
}