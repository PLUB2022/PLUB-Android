package com.plub.presentation.state.createGathering

import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.state.PageState

data class CreateGatheringDayAndOnOfflineAndLocationPageState(
    val gatheringDays: HashSet<String> = hashSetOf(),
    val gatheringOnOffline: String = "",
    val gatheringLocationData: KakaoLocationInfoDocumentVo? = null,
    val gatheringHour: Int = 0,
    val gatheringMin: Int = 0,
    val gatheringFormattedTime: String = ""
) : PageState {
    val isNextButtonEnabled = gatheringDays.isNotEmpty() && gatheringFormattedTime.isNotEmpty() &&
            if(gatheringOnOffline == OnOfflineType.OFF.value)
                gatheringLocationData != null
            else gatheringOnOffline == OnOfflineType.ON.value
}