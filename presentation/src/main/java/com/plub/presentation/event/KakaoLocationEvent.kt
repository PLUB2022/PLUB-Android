package com.plub.presentation.event

import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo

sealed class KakaoLocationEvent : Event {
    object HideKeyboard : KakaoLocationEvent()
    object ScrollToTop : KakaoLocationEvent()
    data class NotifyItemChanged(val position:Int): KakaoLocationEvent()
    data class ConfirmDialog(val data: KakaoLocationInfoDocumentVo?) : KakaoLocationEvent()
}