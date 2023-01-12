package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.presentation.state.PageState

data class CreateGatheringKakaoLocationBottomSheetPageState(
    val searchText: String = "",
    val searchResultCount: Int = 0,
    val selectedLocation: KakaoLocationInfoDocumentVo? = null,
    val showSearchDescription: Boolean = true
) : PageState {
    val isOkButtonEnabled = selectedLocation != null
    val isOkButtonVisible = searchResultCount != 0
    val showEmptyResult = showSearchDescription.not() && searchResultCount == 0
}