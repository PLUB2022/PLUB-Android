package com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation

import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.ui.PageState

data class CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState(
    val gatheringDays: HashSet<DaysType> = hashSetOf(),
    val gatheringOnOffline: String = "",
    val gatheringLocationData: KakaoLocationInfoDocumentVo? = null,
    val gatheringHour: Int = 19,
    val gatheringMin: Int = 0,
    val gatheringFormattedTime: String = ""
) : PageState {
    val isNextButtonEnabled = gatheringDays.isNotEmpty() && gatheringFormattedTime.isNotEmpty() &&
            if(gatheringOnOffline == OnOfflineType.OFF.value)
                gatheringLocationData != null
            else gatheringOnOffline == OnOfflineType.ON.value
}