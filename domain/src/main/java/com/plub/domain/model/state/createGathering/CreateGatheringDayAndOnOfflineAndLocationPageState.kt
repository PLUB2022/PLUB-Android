package com.plub.domain.model.state.createGathering

import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.state.PageState
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo

data class CreateGatheringDayAndOnOfflineAndLocationPageState(
    val gatheringDays: HashSet<String> = hashSetOf(),
    val gatheringOnOffline: String = "",
    val gatheringLocationData: KakaoLocationInfoDocumentVo? = null,
) : PageState {
    val isNextButtonEnabled = gatheringDays.isNotEmpty() &&
            if(gatheringOnOffline == OnOfflineType.OFF.value)
                gatheringLocationData != null
            else gatheringOnOffline == OnOfflineType.ON.value
}