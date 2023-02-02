package com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.ui.Event

sealed class KakaoLocationEvent : Event {
    object HideKeyboard : KakaoLocationEvent()
    object ScrollToTop : KakaoLocationEvent()
    data class NotifyItemChanged(val position:Int): KakaoLocationEvent()
    data class ConfirmDialog(val data: KakaoLocationInfoDocumentVo?) : KakaoLocationEvent()
}