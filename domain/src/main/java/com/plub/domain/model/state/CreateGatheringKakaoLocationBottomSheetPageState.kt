package com.plub.domain.model.state

import androidx.paging.PagingData
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo

data class CreateGatheringKakaoLocationBottomSheetPageState(
    val searchText: String = "",
    val searchResultCount: Int = 0,
    val selectedLocation: KakaoLocationInfoDocumentVo? = null
) : PageState {
    val isOkButtonEnabled = selectedLocation != null
    val isOkButtonVisible = searchResultCount != 0
}